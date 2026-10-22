import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        sortExample();
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
