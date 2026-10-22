
import java.util.ArrayList;

public class Shop {
    private ArrayList<Repair> repairs;

    public Shop(ArrayList<Repair> repairs) {
        this.repairs = repairs;
    }

    public void showRepairs() {
        for (Repair repair : repairs) {
            repair.display();
        }
    }
}
