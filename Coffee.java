public class Coffee extends MenuItem {
    private String description;

    public Coffee(String name, double price, String description) {
        super(name, price);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return getName() + " - $" + getPrice();
    }
}
