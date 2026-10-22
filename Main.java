import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        sortExample();
        randomExample();
        timingExample();
    }

    // Q3: Try both ascending and descending sorts.
    private static void sortExample() {
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

        Collections.sort(data, Collections.reverseOrder());
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i - 1) < data.get(i)) {
                throw new IllegalStateException("Descending sort failed");
            }
        }
        System.out.println("Descending: " + data + "; sorted = true");
    }

    // Q4 and bonus: count the values and compare their mean with zero.
    private static void randomExample() {
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

        double total = 0.0;
        for (float value : sourceData) {
            if (value < -1000.0F || value >= 1000.0F) {
                throw new IllegalStateException("Random value outside requested range");
            }
            total += value;
        }
        double average = total / sourceData.size();
        // Uniform [-1000, 1000) has mean 0 and standard deviation 1000/sqrt(3).
        double standardError = 1000.0 / Math.sqrt(3.0 * n);
        System.out.printf(java.util.Locale.US,
                "Bonus: expected mean = 0; sample mean = %.6f; within 3 standard errors = %b%n",
                average, Math.abs(average) <= 3 * standardError);
    }

    // Q5: Only the summation is inside the timed interval.
    private static void timingExample() {
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
    }

    // Iterator traversal also checks a LinkedList in O(n) time.
    public static boolean isSorted(List<Integer> data) {
        Integer previous = null;
        for (Integer current : data) {
            if (previous != null && previous > current) {
                return false;
            }
            previous = current;
        }
        return true;
    }
}
