public class OrderItem {
    private MenuItem item;
    private int quantity;

    public OrderItem(MenuItem item) {
        this.item = item;
        this.quantity = 1;
    }

    public MenuItem getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        if (quantity > 1) {
            quantity--;
        }
    }

    @Override
    public String toString() {
        return item.getName() + " - $" + item.getPrice() + " x " + quantity;
    }
}
