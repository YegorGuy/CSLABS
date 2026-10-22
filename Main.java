
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Q3
        ArrayList<Integer> data = new ArrayList<>();
        data.add(13);
        data.add(3);
        data.add(32);

        Collections.sort(data);
        System.out.println("Q3: " + data);
        System.out.println("Sorted: " + isSorted(data));

        // Q4
        Random random = new Random(1622);
        ArrayList<Float> numbers = new ArrayList<>();

        int n = 1000;

        for (int i = 0; i < n; i++) {
            numbers.add(random.nextFloat(-1000.0F, 1000.0F));
        }

        System.out.println("Q4 correct count: " + (numbers.size() == n));

        // Bonus
        double total = 0;

        for (float value : numbers) {
            total += value;
        }

        System.out.println("Bonus average: " + total / n);
    }

    public static boolean isSorted(List<Integer> data) {
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i) < data.get(i - 1)) {
                return false;
            }
        }
        return true;
    }

    
    public static void repairShopDemo() {
        ArrayList<Repair> repairs = new ArrayList<>();

        repairs.add(new Repair(1, "Oil change", 50));
        repairs.add(new Repair(2, "Tire repair", 30));
        repairs.add(new Repair(3, "Brake repair", 150));

        Shop shop = new Shop(repairs);
        shop.showRepairs();
    }

    repairShopDemo();

    
    public static void timingTest() {
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

        System.out.println("Average time: " + averageTime / 3 + " ms");
    }

    timingTest();

}
