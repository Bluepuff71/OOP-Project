package Project;

import java.util.ArrayList;

public class Order implements java.io.Serializable {

    private String customerUsername;

    private ArrayList<Shipment> orderedItems;

    private int authorizationNumber;

    public enum OrderStatus {
        Ordered,
        Ready
    }

    private OrderStatus orderStatus;

    public Order(String customerUsername, ArrayList<Shipment> orderedItems, int authorizationNumber) {
        this.customerUsername = customerUsername;
        this.orderedItems = orderedItems;
        this.authorizationNumber = authorizationNumber;
        this.orderStatus = OrderStatus.Ordered;
    }

    public ArrayList<Shipment> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(ArrayList<Shipment> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public int getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(int authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Display order";
    }
}
