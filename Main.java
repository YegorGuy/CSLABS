
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> data = new ArrayList<>();

        data.add(13);
        data.add(3);
        data.add(32);

        Collections.sort(data);

        System.out.println(data);
        System.out.println("Sorted: " + isSorted(data));
    }

    public static boolean isSorted(List<Integer> data) {
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i) < data.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
