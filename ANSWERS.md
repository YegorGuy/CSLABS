# Lab 8 answers

Times below are actual measurements from the simplified code. Each successful sort/search case averages three trials.

## Q1

O(n): summing every number in a list or finding the largest value. O(n log n): merge sort.

## Q2

Doubling n makes the work about four times larger, so large inputs become slow.

## Q3

Compare neighboring values. If any value is smaller than the previous one, the list is not sorted. The example printed `[3, 13, 32], sorted: true`.

```java
Collections.sort(data);
System.out.println(data + ", sorted: " + isSorted(data));
```

The checking method:

```java
public static boolean isSorted(List<Integer> data) {
    int previous = Integer.MIN_VALUE;
    for (int value : data) {
        if (value < previous) {
            return false;
        }
        previous = value;
    }
    return true;
}
```

## Q4

Compare `size()` to n. The check printed `true`, meaning 1,000 numbers were added.

```java
Random random = new Random(1622);
ArrayList<Float> numbers = new ArrayList<>();
int n = 1000;
for (int i = 0; i < n; i++) {
    numbers.add(random.nextFloat(-1000.0F, 1000.0F));
}
System.out.println("Correct count: " + (numbers.size() == n));
```

## Bonus

I expect an average near 0 because positive and negative values balance out. The measured average was **39.482311**, reasonably near 0 compared with the -1000 to 1000 range. A random sample does not have to average exactly 0.

```java
double total = 0;
for (float value : numbers) {
    total += value;
}
System.out.println("Average (expected near 0): " + total / n);
```

## Q5

Average: **3.129333 ms**. Sum: **1.0090389** in each trial. The exact mathematical answer is **1.0**; repeated float additions accumulate rounding error.

```java
double averageTime = 0;
for (int i = 0; i < 3; i++) {
    long start = System.nanoTime();
    float sum = 0.0F;
    for (int j = 0; j < 1000000; j++) {
        sum += 0.000001F;
    }
    long end = System.nanoTime();
    averageTime += (end - start) / 1000000.0;
    System.out.println("Sum: " + sum);
}
System.out.println("Average time (ms): " + averageTime / 3);
```

## Q6

All times are milliseconds. Sorted-order and search-result checks were true in every successful case. The target was chosen from the middle before sorting.

| N | ArrayList sort | ArrayList search | LinkedList sort | LinkedList search |
| ---: | ---: | ---: | ---: | ---: |
| 1,000 | 0.900867 | 0.034167 | 1.050600 | 0.039367 |
| 100,000 | 34.540033 | 0.018133 | 47.272333 | 1.833433 |
| 1,000,000 | 516.300300 | 0.082500 | 764.143633 | 11.192600 |
| 10,000,000 | 6454.309633 | 0.149300 | 28073.663300 | 330.493400 |
| 100,000,000 | Out of memory | No search | Out of memory | No search |
| 1,000,000,000 | Out of memory | No search | Out of memory | No search |
| 2,000,000,000 | Out of memory | No search | Out of memory | No search |

Out of memory means the size was actually attempted but failed with the configured **512 MiB heap limit**. No search was completed for these failed cases. This is the limit of this experiment, not the machine's absolute capacity.

## Q7

Check the middle of a sorted list. If the target is smaller, search the left half; if larger, search the right half. Repeat until the target is found or the remaining section is empty.

## Q8

100,000,000,000 x 4 = **400,000,000,000 bytes = 400 GB**.

## Q9

The largest successful size tested was **10,000,000** for both lists. At 4 bytes per integer, that is **40,000,000 bytes (0.04 GB)**. Actual Java lists use more memory for Integer objects, references, and linked-list nodes.

## Q10

LinkedList was slower for larger lists, especially for searching. ArrayList can access the middle directly, but LinkedList must walk through links. ArrayList binary search is O(log n); Java's binary search on a large LinkedList uses O(n) link traversals. See the [Java documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Collections.html#binarySearch(java.util.List,T)).

## Submission

The Q3, Q4, and Q5 steps have separate Git commits. Follow README.md to create the **private github.umn.edu repository**, push the commits, and add **SauryaSun**. Understand the answers, write your own responses **in pencil**, and submit a photo plus the repository URL. Lab attendance is required.
