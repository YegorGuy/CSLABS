Set-Location $PSScriptRoot
New-Item -ItemType Directory -Path results -Force | Out-Null
javac Main.java Repair.java Shop.java ListBenchmark.java
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed' }
$examples = java Main
$examples
$examples | Set-Content results\examples.txt
java --version | Set-Content results\java-version.txt
'Heap: 512 MiB. Three trials per successful case. No warm-up.' | Set-Content results\settings.txt

'type,n,sort_ms,search_ms,sorted,found,status' | Set-Content results\timings.csv
foreach ($type in @('ArrayList', 'LinkedList')) {
    foreach ($n in @(1000, 100000, 1000000, 10000000, 100000000, 1000000000, 2000000000)) {
        $row = java -Xmx512m ListBenchmark $type $n
        if ($LASTEXITCODE -ne 0) { throw "Failed: $type N=$n" }
        $row | Set-Content "results\$type-$n.txt"
        $row | Add-Content results\timings.csv
        Write-Host $row
    }
}
