# Lab 8

## Run

```powershell
javac Main.java Repair.java Shop.java ListBenchmark.java
java Main
```

For all ArrayList and LinkedList timing experiments:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\Run-Lab.ps1
```

- `Main.java`: sorting check, random numbers, bonus average, sum timer, repair shop example, and adding/re-sorting.
- `Repair.java` and `Shop.java`: the required classes and constructors.
- `ListBenchmark.java`: averages three sort/search trials for one list type and size. Example: `java -Xmx512m ListBenchmark ArrayList 1000`.
- `Run-Lab.ps1`: runs every required size, plus 100 million and 2 billion, and saves the results.
- `ANSWERS.md`: short answers and required code snippets.
- `results/`: actual measurements.

The benchmark checks sorted order and verifies that binary search found the selected value. The target is chosen from the middle before sorting. Only sort/search calls are timed. Both list types use the same random seeds.

Each size runs in its own Java process with a 512 MiB heap. `OUT_OF_MEMORY` means that case was attempted but did not fit this limit. These are results for this heap setting, not the machine's absolute limit. Rerunning replaces the measurements; update the answers if the times change.

## Submission

The local Git repository has the required intermediate commits. You still need to:

1. Create an empty **private** repository named `lab8` at **https://github.umn.edu**.
2. Replace YOUR_USERNAME below and push:

   ```powershell
   git remote add origin https://github.umn.edu/YOUR_USERNAME/lab8.git
   git push -u origin HEAD
   ```

3. Add **SauryaSun** as a collaborator and submit the repository URL.
4. Understand the answers, write your own responses **in pencil on paper**, and submit a photo with the requested snippets and timing table.
5. Attend and work during lab. The assignment lists October 29, 2026 as the due date.
