import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ListBenchmark {
    public static void main(String[] args) {
        String type = args[0];
        int n = Integer.parseInt(args[1]);
        try {
            benchmark(type, n);
        } catch (OutOfMemoryError e) {
            System.out.println(type + "," + n + ",,,,,OUT_OF_MEMORY");
        }
    }

    public static void benchmark(String type, int n) {
        double sortTime = 0;
        double searchTime = 0;
        boolean sorted = true;
        boolean found = true;

        for (int trial = 0; trial < 3; trial++) {
            List<Integer> data;
            if (type.equals("ArrayList")) {
                data = new ArrayList<>(n);
            } else {
                data = new LinkedList<>();
            }
            Random random = new Random(1622 + trial);
            for (int i = 0; i < n; i++) {
                data.add(random.nextInt());
            }
            int target = data.get(n / 2); // Pick before sorting.

            long start = System.nanoTime();
            Collections.sort(data);
            sortTime += (System.nanoTime() - start) / 1000000.0;
            sorted = sorted && Main.isSorted(data);

            start = System.nanoTime();
            int index = Collections.binarySearch(data, target);
            searchTime += (System.nanoTime() - start) / 1000000.0;
            found = found && index >= 0 && data.get(index) == target;
        }
        System.out.println(type + "," + n + "," + sortTime / 3 + ","
                + searchTime / 3 + "," + sorted + "," + found + ","
                + (sorted && found ? "OK" : "FAILED"));
    }
}
