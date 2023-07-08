import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private Map<String, OrderItem> orderItems;
    private double totalPrice;

    public Order() {
        orderItems = new HashMap<>();
        totalPrice = 0.0;
    }

    public void addItem(MenuItem item) {
        String itemName = item.getName();
        if (orderItems.containsKey(itemName)) {
            OrderItem orderItem = orderItems.get(itemName);
            orderItem.incrementQuantity();
        } else {
            OrderItem orderItem = new OrderItem(item);
            orderItems.put(itemName, orderItem);
        }
        totalPrice += item.getPrice();
    }

    public void removeItem(MenuItem item) {
        String itemName = item.getName();
        if (orderItems.containsKey(itemName)) {
            OrderItem orderItem = orderItems.get(itemName);
            if (orderItem.getQuantity() > 1) {
                orderItem.decrementQuantity();
            } else {
                orderItems.remove(itemName);
            }
            totalPrice -= item.getPrice();
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems.values());
    }
}
