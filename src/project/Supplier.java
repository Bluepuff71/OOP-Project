package project;

import fileStuff.Serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Supplier extends Account implements java.io.Serializable {

    //TODO fix the static fields so they will be serialized
    /**
     * The list of orders that have been created
     */
    private static List<Order> deliveryOrderList;

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
     * Gets the order list
     * @return the order list
     */
    public static List<Order> getDeliveryOrderList() {
        return deliveryOrderList;
    }

    public static List<Shipment> getInventoryOrderList() {
        return inventoryOrderList;
    }

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
