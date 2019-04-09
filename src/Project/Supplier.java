package Project;

import java.util.ArrayList;

public class Supplier extends Account implements java.io.Serializable {

    /**
     * The list of orders that have been created
     */
    private ArrayList<Order> deliveryOrderList;

    /**
     * Creates a supplier account with an empty order list
     *
     * @param username  the username of the supplier
     * @param plainText the plaintext password of the supplier
     */
    public Supplier(String username, String plainText) {
        super(username, plainText);
        this.deliveryOrderList = new ArrayList<>();
    }

    /**
     * Creates a supplier account
     * @param username the username of the supplier
     * @param plainText the plaintext password of the supplier
     * @param deliveryOrderList the list of orders that have been created
     */
    public Supplier(String username, String plainText, ArrayList<Order> deliveryOrderList) {
        super(username, plainText);
        this.deliveryOrderList = deliveryOrderList;
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
            if(storefront.inventoryCheck(shipment.getItem(), shipment.getAmount())){

            } else{
                throw new OutOfStockException();
            }


        }
    }*/
}
