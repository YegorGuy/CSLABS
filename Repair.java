public class Repair {
    private int id;
    private String description;
    private double cost;

    public Repair(int id, String description, double cost) {
        this.id = id;
        this.description = description;
        this.cost = cost;
    }

    public String toString() {
        return id + ": " + description + " ($" + cost + ")";
    }
}
