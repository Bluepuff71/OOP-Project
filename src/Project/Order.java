package Project;

import java.util.ArrayList;

public class Order implements java.io.Serializable {

    private String customerUsername;

    private ArrayList<Shipment> orderItems;

    private int authorizationNumber;

    public enum OrderStatus {
        Ordered,
        Ready
    }

    private OrderStatus orderStatus;

    public Order(String customerUsername, ArrayList<Shipment> orderItems, int authorizationNumber) {
        this.customerUsername = customerUsername;
        this.orderItems = orderItems;
        this.authorizationNumber = authorizationNumber;
        this.orderStatus = OrderStatus.Ordered;
    }

    public ArrayList<Shipment> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<Shipment> orderItems) {
        this.orderItems = orderItems;
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
