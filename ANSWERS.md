# Lab 8 answers

Measured on October 22, 2026 using Java 21. Each successful sort/search result is the average of three trials. Times vary between runs. The saved raw results are in `results/`.

## Q1

O(n): examining every item once, such as summing a list or finding its largest value. O(n log n): efficient comparison sorting, such as merge sort.

## Q2

O(n^2) grows quickly. Doubling the input makes the work about four times larger, so large inputs become slow.

## Q3

Compare each value with the one before it. If an earlier value is larger, the ascending list is not sorted. My example printed `[3, 13, 32]` and `sorted = true`.

```java
ArrayList<Integer> data = new ArrayList<>();
data.add(13);
data.add(3);
data.add(32);
Collections.sort(data);

boolean sorted = true;
for (int i = 1; i < data.size(); i++) {
    if (data.get(i - 1) > data.get(i)) {
        sorted = false;
        break;
    }
}
System.out.println("Ascending: " + data + "; sorted = " + sorted);
if (!sorted) {
    throw new IllegalStateException("Ascending sort failed");
}
```

The code also checks a descending sort in `Main.java`. The Q3 step has its own Git commit.

## Q4

Check that `size()` equals the requested count. The result was 1,000 values.

```java
ArrayList<Float> sourceData = new ArrayList<>();
Random prng = new Random(1622L);
int n = 1000;
for (int i = 0; i < n; i++) {
    sourceData.add(prng.nextFloat(-1000.0F, 1000.0F));
}

if (sourceData.size() != n) {
    throw new IllegalStateException("Wrong number of random values");
}
System.out.println("Random count: " + sourceData.size() + "; expected: " + n);
```

The Q4 step has its own Git commit.

## Bonus

I expect an average near 0 because the range is symmetric. My sample average was **39.482311**, which is within three standard errors of 0 (about 54.77). This supports the estimate; a finite random sample does not have to average exactly 0.

```java
double total = 0.0;
for (float value : sourceData) {
    if (value < -1000.0F || value >= 1000.0F) {
        throw new IllegalStateException("Random value outside requested range");
    }
    total += value;
}
double average = total / sourceData.size();
double standardError = 1000.0 / Math.sqrt(3.0 * n);
System.out.printf(java.util.Locale.US,
        "Bonus: expected mean = 0; sample mean = %.6f; within 3 standard errors = %b%n",
        average, Math.abs(average) <= 3 * standardError);
```

## Q5

Average: **3.276333 ms**. Each trial gave **1.0090389**. The mathematical answer is **1.0**, so the result is not exact because repeated `float` additions accumulate rounding error.

```java
double avgTimeMs = 0.0;
int n = 3;
for (int i = 0; i < n; i++) {
    long startTime = System.nanoTime();
    float sum = 0.0F;
    for (int j = 0; j < 1000000; j++) {
        sum += 0.000001F;
    }
    long endTime = System.nanoTime();
    long diffTime = endTime - startTime;
    System.out.println("sum = " + sum);
    avgTimeMs += diffTime / 1000000.0;
}
avgTimeMs /= n;
System.out.printf(java.util.Locale.US, "Average time: %.6f ms%n", avgTimeMs);
```

The Q5 step has its own Git commit.

## Q6

All times below are **milliseconds**. Each successful trial verified both sorted order and the value returned by binary search. The search target came from the middle of the unsorted list.

| N | ArrayList sort | ArrayList search | LinkedList sort | LinkedList search |
| ---: | ---: | ---: | ---: | ---: |
| 1,000 | 0.350167 | 0.003267 | 0.331933 | 0.011400 |
| 100,000 | 29.009467 | 0.014167 | 33.940467 | 0.892233 |
| 1,000,000 | 393.506500 | 0.039967 | 663.820967 | 12.048067 |
| 10,000,000 | 6576.095367 | 0.065700 | 8541.378433 | 113.088967 |
| 100,000,000 | Out of memory | No search | Out of memory | No search |
| 1,000,000,000 | Out of memory | No search | Out of memory | No search |
| 2,000,000,000 | Out of memory | No search | Out of memory | No search |

The JVM heap was deliberately limited to **512 MiB (536,870,912 bytes)**. Failed cases actually attempted allocation; no timing is reported for an unfinished operation. These failures describe this heap configuration, not the machine's maximum possible capacity. Every tested size used its own JVM.

## Q7

Binary search checks the middle of a sorted list. If the target is smaller, it searches the left half; if larger, the right half. It repeats until it finds the target or no values remain.

## Q8

100,000,000,000 x 4 = **400,000,000,000 bytes**, or **400 GB** using 1 GB = 1 billion bytes.

## Q9

The largest successful N tested was **10,000,000** for both lists. At 4 bytes per integer, the values require **40,000,000 bytes (0.04 GB)**.

That is only the integer payload. Java's `Integer` objects, references, LinkedList nodes, and sorting workspace require additional memory; the actual list uses more than 4N bytes. The first larger tested size, 100,000,000, failed at the configured heap limit.

## Q10

LinkedList sorting was slower for the larger successful inputs. At 10 million values it took about **8541 ms**, versus **6576 ms** for ArrayList. LinkedList search was much slower: **113.089 ms**, versus **0.0657 ms**.

ArrayList can jump directly to a middle index. LinkedList must traverse links; Java's binary search on a large LinkedList takes O(n) link traversals, although it makes O(log n) comparisons. ArrayList binary search takes O(log n) time. See the [official Java Collections documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html#binarySearch(java.util.List,T)). Small timings also include JVM and timer noise, so individual rows need not follow a perfect curve.

## Other required examples

The `Shop` constructor receives the ArrayList containing three Repair objects. The example prints all repairs and a total cost of **$330.00**.

The extra add-and-re-sort experiment adds 25 numbers, one at a time, to an already sorted ArrayList and sorts again after every addition. Average add-and-sort times from `results/examples.txt`:

| Starting N | Average ms per addition and re-sort |
| ---: | ---: |
| 1,000 | 0.046800 |
| 10,000 | 0.067448 |
| 100,000 | 0.358112 |

Larger lists generally take longer to re-sort repeatedly. Mostly sorted input can be processed faster than fresh random input by Java's adaptive sorting implementation; these timings are not interchangeable with the random-list sort timings.

## Submission

The remote repository URL is **not available yet**. Follow `README.md` to create the required **private github.umn.edu repository**, push these commits, and add **SauryaSun**. Review the answers and write your own responses **in pencil on paper**, then submit the photo and repository URL. Lab attendance is also required.
