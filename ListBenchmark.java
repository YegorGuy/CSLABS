import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

// Run one list type and size per JVM, so a memory failure cannot stop other sizes.
public class ListBenchmark {
    public static void main(String[] args) {
        if (args.length != 3 || !(args[0].equals("ArrayList") || args[0].equals("LinkedList"))) {
            throw new IllegalArgumentException("Usage: java ListBenchmark ArrayList|LinkedList N repetitions");
        }
        String type = args[0];
        int n = Integer.parseInt(args[1]);
        int repetitions = Integer.parseInt(args[2]);
        if (n < 1 || repetitions < 1) {
            throw new IllegalArgumentException("N and repetitions must be positive");
        }
        System.out.println("Heap limit bytes: " + Runtime.getRuntime().maxMemory());
        // Small untimed warm-up. Each measured trial still uses fresh random data.
        runTrial(type, 10000, -1, false);
        System.gc();
        double sortTotal = 0.0;
        double searchTotal = 0.0;
        for (int trial = 0; trial < repetitions; trial++) {
            double[] times = runTrial(type, n, trial, true);
            sortTotal += times[0];
            searchTotal += times[1];
            System.out.printf(Locale.US, "TRIAL,%s,%d,%d,%.6f,%.6f%n",
                    type, n, trial + 1, times[0], times[1]);
            System.gc(); // Outside measured intervals, after the old list is unreachable.
        }
        System.out.printf(Locale.US, "RESULT,%s,%d,%d,%.6f,%.6f,OK%n",
                type, n, repetitions, sortTotal / repetitions, searchTotal / repetitions);
    }

    private static double[] runTrial(String type, int n, int trial, boolean reportPhase) {
        if (reportPhase) {
            System.out.println("Phase: generating " + n + " " + type + " values");
        }
        List<Integer> data;
        if (type.equals("ArrayList")) {
            data = new ArrayList<>(n);
        } else {
            data = new LinkedList<>();
        }
        // Identical seeds give both list types identical values for each trial.
        Random prng = new Random(1622L + trial);
        for (int i = 0; i < n; i++) {
            data.add(prng.nextInt());
        }
        if (data.size() != n) {
            throw new IllegalStateException("Wrong number of integers");
        }
        int target = data.get(n / 2); // Select from the UNSORTED list.
        if (reportPhase) {
            System.out.println("Phase: sorting");
        }
        long start = System.nanoTime();
        Collections.sort(data);
        long sortNanos = System.nanoTime() - start;
        if (!Main.isSorted(data)) {
            throw new IllegalStateException("List is not sorted");
        }
        if (reportPhase) {
            System.out.println("Phase: searching");
        }
        start = System.nanoTime();
        int index = Collections.binarySearch(data, target);
        long searchNanos = System.nanoTime() - start;
        if (index < 0 || data.get(index).intValue() != target) {
            throw new IllegalStateException("Binary search returned the wrong value");
        }
        return new double[] {sortNanos / 1000000.0, searchNanos / 1000000.0};
    }
}
