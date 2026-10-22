import java.util.ArrayList;

public class Shop {
    private final ArrayList<Repair> repairs;

    public Shop(ArrayList<Repair> repairs) {
        this.repairs = new ArrayList<>(repairs);
    }

    public ArrayList<Repair> getRepairs() {
        return new ArrayList<>(repairs);
    }

    public double getTotalCost() {
        double total = 0.0;
        for (Repair repair : repairs) {
            total += repair.getCost();
        }
        return total;
    }
}
