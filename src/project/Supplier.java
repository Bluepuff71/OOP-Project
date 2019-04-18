package project;

import java.util.ArrayList;
import java.util.List;

public final class Supplier extends Account implements java.io.Serializable {

    /**
     * The list of orders that have been created
     */
    private static List<Order> deliveryOrderList = new ArrayList<>();

    /**
     * Creates a supplier account with an empty order list
     *
     * @param username  the username of the supplier
     * @param plainText the plaintext password of the supplier
     */
    public Supplier(String username, String plainText) {
        super(username, plainText);
    }

    /**
     * Adds an order to the order list
     * @param order the order to add
     */
    public static void addToDeliveryOrderList(Order order) {
        deliveryOrderList.add(order);
    }

    /**
     * Removes an order from the order list
     * @param order the order to remove
     */
    public static void removeFromDeliveryOrderList(Order order) {
        deliveryOrderList.remove(order);
    }

    /**
     * Gets the order list
     * @return the order list
     */
    public static List<Order> getDeliveryOrderList() {
        return deliveryOrderList;
    }

}
