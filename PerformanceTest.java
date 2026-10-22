
import java.util.*;

public class PerformanceTest {

    static final int[] SIZES = {
        1000,
        100000,
        1000000,
        10000000,
        100000000,
        1000000000
    };

    static final int TRIALS = 3;

    public static void main(String[] args) {
        for (int n : SIZES) {
            System.out.println("\nN = " + n);

            testList(n, false);
            testList(n, true);
        }
    }

    public static void testList(int n, boolean linked) {
        String name = linked ? "LinkedList" : "ArrayList";

        double sortTotal = 0;
        double searchTotal = 0;

        try {
            for (int trial = 0; trial < TRIALS; trial++) {
                List<Integer> data = linked
                    ? new LinkedList<>()
                    : new ArrayList<>();

                Random random = new Random(1622 + trial);

                for (int i = 0; i < n; i++) {
                    data.add(random.nextInt());
                }

                // Pick a target before sorting
                int target = data.get(n / 2);

                // Time sorting
                long start = System.nanoTime();
                Collections.sort(data);
                long end = System.nanoTime();

                sortTotal += (end - start) / 1000000.0;

                // Time binary search
                start = System.nanoTime();
                int index = Collections.binarySearch(data, target);
                end = System.nanoTime();

                searchTotal += (end - start) / 1000000.0;

                // Check result
                if (index < 0 || !data.get(index).equals(target)) {
                    System.out.println("Search failed!");
                    return;
                }

                // Check sorting
                if (!isSorted(data)) {
                    System.out.println("Sort failed!");
                    return;
                }
            }

            System.out.printf(
                "%s sort: %.6f ms%n",
                name, sortTotal / TRIALS
            );

            System.out.printf(
                "%s search: %.6f ms%n",
                name, searchTotal / TRIALS
            );

        } catch (OutOfMemoryError e) {
            System.out.println(name + ": Out of memory");
        }
    }

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
}
