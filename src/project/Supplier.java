package project;

import java.util.ArrayList;
import java.util.List;

public final class Supplier extends Account implements java.io.Serializable {

    //TODO fix the static fields so they will be serialized
    /**
     * The list of orders that have been created
     */
    private List<Order> deliveryOrderList = new ArrayList<>();

    private List<Shipment> inventoryOrderList = new ArrayList<>();

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
    public List<Order> getDeliveryOrderList() {
        return deliveryOrderList;
    }

    public List<Shipment> getInventoryOrderList() {
        return inventoryOrderList;
    }

    public void addToInventoryOrderList(Shipment shipment) {
        for (Shipment selectedShipment : inventoryOrderList) {
            if (selectedShipment.getItem() == shipment.getItem()) {
                selectedShipment.setAmount(selectedShipment.getAmount() + shipment.getAmount());
                return;
            }
        }
        inventoryOrderList.add(shipment);
    }
}
