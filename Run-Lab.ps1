param(
    [string]$Heap = '512m',
    [int]$Repetitions = 3,
    [int]$TimeoutSeconds = 120,
    [int[]]$Sizes = @(1000, 100000, 1000000, 10000000, 100000000, 1000000000, 2000000000)
)

$ErrorActionPreference = 'Stop'
Push-Location -LiteralPath $PSScriptRoot
try {
    New-Item -ItemType Directory -Path build -Force | Out-Null
    New-Item -ItemType Directory -Path results -Force | Out-Null
    & javac -Xlint:all -d build Main.java Repair.java Shop.java ListBenchmark.java
    if ($LASTEXITCODE -ne 0) { throw 'Compilation failed.' }
    & java -cp build Main | Tee-Object -FilePath results\examples.txt
    if ($LASTEXITCODE -ne 0) { throw 'Examples failed.' }
    & java -version 2>&1 | Out-File results\java-version.txt
    "Heap=$Heap; repetitions=$Repetitions; timeout=$TimeoutSeconds seconds; date=$(Get-Date -Format o)" |
        Set-Content results\settings.txt
    'type,n,completed_trials,sort_ms,search_ms,status' | Set-Content results\timings.csv

    foreach ($type in @('ArrayList', 'LinkedList')) {
        foreach ($n in $Sizes) {
            Write-Host "Running $type N=$n ..."
            $startInfo = New-Object System.Diagnostics.ProcessStartInfo
            $startInfo.FileName = (Get-Command java).Source
            $startInfo.Arguments = "-Xmx$Heap -XX:+ExitOnOutOfMemoryError -cp build ListBenchmark $type $n $Repetitions"
            $startInfo.WorkingDirectory = $PSScriptRoot
            $startInfo.UseShellExecute = $false
            $startInfo.CreateNoWindow = $true
            $startInfo.RedirectStandardOutput = $true
            $startInfo.RedirectStandardError = $true
            $process = New-Object System.Diagnostics.Process
            $process.StartInfo = $startInfo
            [void]$process.Start()
            $stdoutTask = $process.StandardOutput.ReadToEndAsync()
            $stderrTask = $process.StandardError.ReadToEndAsync()
            $finished = $process.WaitForExit($TimeoutSeconds * 1000)
            if (-not $finished) {
                $process.Kill()
                $process.WaitForExit()
            }
            $log = $stdoutTask.Result + $stderrTask.Result
            $log | Set-Content "results\$type-$n.txt"
            $resultLine = @($log -split '\r?\n' | Where-Object { $_.StartsWith('RESULT,') })
            if ($finished -and $process.ExitCode -eq 0 -and $resultLine.Count -eq 1) {
                $row = $resultLine[0].Substring(7)
            } else {
                $completed = @($log -split '\r?\n' | Where-Object { $_.StartsWith('TRIAL,') }).Count
                if (-not $finished) {
                    $status = 'TIMEOUT'
                } elseif ($log -match 'OutOfMemoryError') {
                    $status = 'OUT_OF_MEMORY'
                } else {
                    throw "Unexpected benchmark failure: $type N=$n. Read results\$type-$n.txt"
                }
                $row = "$type,$n,$completed,,,$status"
            }
            $row | Add-Content results\timings.csv
            Write-Host $row
            $process.Dispose()
        }
    }
} finally {
    Pop-Location
}
