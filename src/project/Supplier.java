package project;

import java.util.ArrayList;

public final class Supplier extends Account implements java.io.Serializable {

    /**
     * The list of orders that have been created
     */
    private static ArrayList<Order> deliveryOrderList = new ArrayList<>();

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
    public void addToDeliveryOrderList(Order order) {
        deliveryOrderList.add(order);
    }

    /**
     * Removes an order from the order list
     * @param order the order to remove
     */
    public void removeFromDeliveryOrderList(Order order) {
        deliveryOrderList.remove(order);
    }

    /**
     * Gets the order list
     * @return the order list
     */
    public ArrayList<Order> getDeliveryOrderList() {
        return deliveryOrderList;
    }

    /*public void processDeliveryOrder(Order order, Storefront storefront){

        for(Shipment shipment : order.getOrderedItems()){
            if(storefront.itemInStock(shipment.getItem(), shipment.getAmount())){

            } else{
                throw new OutOfStockException();
            }


        }
    }*/
}
