public class Repair {
    private final int id;
    private final String description;
    private final double cost;

    public Repair(int id, String description, double cost) {
        this.id = id;
        this.description = description;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.US, "%d: %s ($%.2f)", id, description, cost);
    }
}
