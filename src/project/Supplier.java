package project;

import fileStuff.Serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The account that suppliers use to interact with orders
 */
public final class Supplier extends Account implements java.io.Serializable {

    /**
     * The list of delivery orders that have been created
     */
    private static List<Order> deliveryOrderList;

    /**
     * The list of inventory orders that have been created
     */
    private static List<Shipment> inventoryOrderList;

    static {
        try{
            deliveryOrderList = Serializer.readObject("DelOrder.dat");
        } catch (IOException e){
            deliveryOrderList = new ArrayList<>();
        } catch (ClassNotFoundException e){
            System.out.println("You shouldn't see this error");
        }
        try{
            inventoryOrderList = Serializer.readObject("InvOrder.dat");
        } catch (IOException e){
            inventoryOrderList = new ArrayList<>();
        } catch (ClassNotFoundException e){
            System.out.println("You shouldn't see this error");
        }
    }

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
     * Gets the delivery order list
     * @return the delivery order list
     */
    public static List<Order> getDeliveryOrderList() {
        return deliveryOrderList;
    }

    /**
     * Gets the inventory order list
     *
     * @return the inventory order list
     */
    public static List<Shipment> getInventoryOrderList() {
        return inventoryOrderList;
    }

    /**
     * Adds a shipment to the inventory order list (additive)
     * @param shipment the shipment to add
     */
    public static void addToInventoryOrderList(Shipment shipment) {
        for (Shipment selectedShipment : inventoryOrderList) {
            if (selectedShipment.getItem() == shipment.getItem()) {
                selectedShipment.setAmount(selectedShipment.getAmount() + shipment.getAmount());
                return;
            }
        }
        inventoryOrderList.add(shipment);
    }
}
