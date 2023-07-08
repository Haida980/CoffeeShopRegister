import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App extends JFrame {
    private MenuItem[] menu;
    private Order currentOrder;
    private DefaultListModel<String> menuListModel;
    private DefaultListModel<OrderItem> orderListModel;
    private JLabel totalPriceLabel;
    private JList<OrderItem> orderList;
    private JButton createOrderButton;
    private JButton removeButton;

    public App() {
        menu = new MenuItem[10];
        menu[0] = new Coffee("Espresso", 2.5, "A concentrated coffee beverage.");
        menu[1] = new Coffee("Cappuccino", 3.0, "A coffee-based drink prepared with espresso and steamed milk foam.");
        menu[2] = new Coffee("Latte", 3.5, "A coffee drink made with espresso and steamed milk.");
        menu[3] = new Coffee("Mocha", 4.0, "An espresso-based drink with chocolate and steamed milk.");
        menu[4] = new Coffee("Americano", 2.75, "A coffee drink made with espresso and hot water.");
        menu[5] = new Coffee("Macchiato", 3.25, "An espresso-based drink with a small amount of milk foam.");
        menu[6] = new Coffee("Affogato", 4.5, "A dessert coffee with a scoop of ice cream.");
        menu[7] = new Coffee("Drip Coffee", 2.0, "A traditional method of brewing coffee.");
        menu[8] = new Coffee("Iced Coffee", 3.75, "Chilled coffee served with ice.");
        menu[9] = new Coffee("Flat White", 3.25, "A coffee drink with microfoam and a double shot of espresso.");

        currentOrder = new Order();
    }

    public void createAndShowGUI() {
        setTitle("Coffee Shop Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel menuLabel = new JLabel("Menu:");
        JList<String> menuList = new JList<>();
        menuListModel = new DefaultListModel<>();
        for (MenuItem item : menu) {
            menuListModel.addElement(item.toString());
        }
        menuList.setModel(menuListModel);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane menuScrollPane = new JScrollPane(menuList);

        JButton addButton = new JButton("Add to Order");
        addButton.addActionListener(e -> {
            int selectedIndex = menuList.getSelectedIndex();
            if (selectedIndex >= 0) {
                currentOrder.addItem(menu[selectedIndex]);
                updateOrderList();
                updateTotalPriceLabel();
                toggleCreateOrderButtonVisibility();
            }
        });

        JLabel orderLabel = new JLabel("Order:");
        orderList = new JList<>();
        orderListModel = new DefaultListModel<>();

        orderList.setModel(orderListModel);
        orderList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        removeButton = new JButton("Remove Item");
        removeButton.setVisible(false);
        removeButton.addActionListener(e -> {
            int selectedIndex = orderList.getSelectedIndex();
            if (selectedIndex >= 0) {
                OrderItem orderItem = orderListModel.getElementAt(selectedIndex);
                MenuItem item = orderItem.getItem();
                currentOrder.removeItem(item);
                updateOrderList();
                updateTotalPriceLabel();
                toggleCreateOrderButtonVisibility();
            }
        });

        createOrderButton = new JButton("Create Order");
        createOrderButton.setVisible(false);
        createOrderButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Order Created.Your coffee will be ready in 5 minutes!");
            currentOrder = new Order();
            updateOrderList();
            updateTotalPriceLabel();
            toggleCreateOrderButtonVisibility();
        });

        JLabel totalPriceTextLabel = new JLabel("Total Price:");
        totalPriceLabel = new JLabel("$0.0");

        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.add(menuLabel, BorderLayout.NORTH);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);
        menuPanel.add(addButton, BorderLayout.SOUTH);

        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.add(orderLabel, BorderLayout.NORTH);
        orderPanel.add(new JScrollPane(orderList), BorderLayout.CENTER);
        orderPanel.add(removeButton, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(createOrderButton);

        JPanel totalPricePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPricePanel.add(totalPriceTextLabel);
        totalPricePanel.add(totalPriceLabel);

        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(orderPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(totalPricePanel, BorderLayout.SOUTH);

        menuList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = menuList.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        MenuItem menuItem = menu[selectedIndex];
                        JOptionPane.showMessageDialog(App.this, menuItem.getDescription(), "About Coffee", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        orderList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = orderList.getSelectedIndex();
                removeButton.setVisible(selectedIndex >= 0);
            }
        });

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateOrderList() {
        orderListModel.clear();
        for (OrderItem item : currentOrder.getOrderItems()) {
            orderListModel.addElement(item);
        }
        orderList.setModel(orderListModel);
    }

    private void updateTotalPriceLabel() {
        totalPriceLabel.setText("$" + currentOrder.getTotalPrice());
    }

    private void toggleCreateOrderButtonVisibility() {
        createOrderButton.setVisible(!currentOrder.getOrderItems().isEmpty());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App register = new App();
            register.createAndShowGUI();
        });
    }
}



