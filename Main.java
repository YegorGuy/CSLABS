import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Q3: Sort and check the order.
        ArrayList<Integer> data = new ArrayList<>();
        data.add(13);
        data.add(3);
        data.add(32);
        Collections.sort(data);
        System.out.println(data + ", sorted: " + isSorted(data));

        // Q4 and bonus: Generate 1000 numbers, count them, and find the average.
        Random random = new Random(1622);
        ArrayList<Float> numbers = new ArrayList<>();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            numbers.add(random.nextFloat(-1000.0F, 1000.0F));
        }
        System.out.println("Correct count: " + (numbers.size() == n));
        double total = 0;
        for (float value : numbers) {
            total += value;
        }
        System.out.println("Average (expected near 0): " + total / n);

        // Q5: Time the sum three times.
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

        // Pass an ArrayList of repairs to the Shop constructor.
        ArrayList<Repair> repairs = new ArrayList<>();
        repairs.add(new Repair(1, "Replace tire", 85.00));
        repairs.add(new Repair(2, "Change oil", 45.00));
        repairs.add(new Repair(3, "Replace brakes", 200.00));
        Shop shop = new Shop(repairs);
        shop.showRepairs();

        // Add one value to a sorted list and re-sort, 25 times.
        for (int size : new int[] {1000, 10000, 100000}) {
            ArrayList<Integer> values = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                values.add(random.nextInt());
            }
            Collections.sort(values);
            double time = 0;
            for (int i = 0; i < 25; i++) {
                int value = random.nextInt();
                long start = System.nanoTime();
                values.add(value);
                Collections.sort(values);
                time += (System.nanoTime() - start) / 1000000.0;
            }
            System.out.println("Add and re-sort, N=" + size + ": " + time / 25 + " ms");
        }
    }

    // A for-each loop works efficiently for both ArrayList and LinkedList.
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
