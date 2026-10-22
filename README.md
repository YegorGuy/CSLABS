# Lab 8: Empirically Understanding Performance Related to Big-O

All lab files are in this folder. Requires JDK 17 or newer; the saved run used Java 21.

## Run the examples

```powershell
javac Main.java Repair.java Shop.java ListBenchmark.java
java Main
```

This runs the Q3 sorting checks, Q4 random-number count and bonus mean, Q5 summation timer, Repair/Shop constructor example, and repeated add-and-re-sort experiment.

## Run the full timing experiment

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\Run-Lab.ps1
```

The script compiles the code, runs the examples, and tests both ArrayList and LinkedList at N = 1,000; 100,000; 1,000,000; 10,000,000; 100,000,000; 1,000,000,000; and 2,000,000,000. Each successful case averages three fresh random lists. Both list types use the same random seeds. An untimed warm-up precedes each case. Data generation, validation, printing, and explicit garbage collection are outside the timed sort/search intervals. Every search target is selected from the middle of the unsorted list.

Each case runs in a separate JVM with a **512 MiB heap limit** and a 120-second timeout. This keeps failed large allocations from stopping the entire experiment. OUT_OF_MEMORY means the case actually ran and hit that limit; TIMEOUT means it was stopped without a completed result. These are limits of this configured experiment, not proof that the machine could never handle more with different settings. The machine reported about 7.7 GiB of physical RAM, with less than 1 GiB free before the experiment.

To change the heap or test sizes:

```powershell
& .\Run-Lab.ps1 -Heap 1g -Sizes @(1000,100000,1000000)
```

This example runs inside a PowerShell session that permits scripts. Keep the heap within available memory. Rerunning replaces the saved results; update ANSWERS.md if your measurements change.

## Files

- `Main.java`: introductory examples, shop demonstration, and add/re-sort experiment.
- `Repair.java`, `Shop.java`: required repair-shop classes and constructor.
- `ListBenchmark.java`: timed sort/search trials and correctness checks.
- `Run-Lab.ps1`: builds and runs the complete experiment.
- `results/timings.csv`: measured averages and failures.
- `results/*.txt`: raw trial logs, example output, Java version, and run settings.
- `ANSWERS.md`: brief answers to Q1-Q10, bonus, and requested code snippets.

## Required submission steps

This folder is a separate local Git repository with commits for Q3, Q4, Q5, and the remaining work. A university GitHub repository has not been created or shared yet.

1. Sign in to **https://github.umn.edu** and create a **PRIVATE** repository named `lab8`. Leave it empty (no generated README, license, or .gitignore).
2. In this folder, replace YOUR_USERNAME with your university GitHub username and run:

   ```powershell
   git remote add origin https://github.umn.edu/YOUR_USERNAME/lab8.git
   git push -u origin HEAD
   ```

3. In that repository's settings, add **SauryaSun** as a collaborator. Sharing with the TA is explicitly required by the assignment.
4. Submit the actual repository URL to Canvas.
5. Review and understand the answers, write them **in pencil on paper**, and submit a photo. Include the requested code snippets and timing table. The assignment says answers must be your own; use this work to understand the results before writing your responses.
6. Attend and work during the scheduled lab for the attendance portion of the grade. The assignment page lists **October 29, 2026** as the due date.

Do not publish this lab to the parent folder's github.com repository: the assignment specifically requires a private repository on github.umn.edu.

Java search behavior is documented in the [official Collections API](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html#binarySearch(java.util.List,T)).
