package project;

import fileStuff.Serializer;

import java.io.IOException;
import java.util.ArrayList;

public class InventoryManager implements java.io.Serializable {

    /**
     * The inventory of the storefront
     */
    private ArrayList<Shipment> inventory;

    public InventoryManager(String fileName) {
        try {
            inventory = Serializer.readObject(fileName);
        } catch (IOException e) {
            inventory = new ArrayList<>();
        } catch (Exception e) {
            System.out.println("There was an error.");
        }
    }

    /**
     * Creates an order request from a customers cart
     *
     * @param customer the customer that is creating the order
     * @return the order that is created
     * @throws ItemNotFoundException if one of the items in the cart is invalid
     * @throws InvalidCardException  if the customer's card number is not valid
     * @throws CreditLimitException  if the purchase would exceed the customer's credit limit
     */
    public Order createOrderRequest(Customer customer) throws ItemNotFoundException, InvalidCardException, CreditLimitException {
        //Check if the items exist in the store
        for (Shipment shipment : customer.getCart()) {
            if (!itemInStock(shipment.getItem(), 1)) {
                throw new ItemNotFoundException();
            }
        }
        return new Order(customer.getUsername(), customer.getCart(), Bank.getPurchaseAuthorizationNumber(customer.getCard(), customer.getCartTotalCost()));
    }

    /**
     * Checks the inventory for a specified amount of an item
     *
     * @param item   the item to check
     * @param amount the amount to check
     * @return true if there is enough supply in the inventory
     */
    public boolean itemInStock(Item item, int amount) {
        try {
            return getShipmentFromInventory(item).getAmount() >= amount;
        } catch (ItemNotFoundException e) {
            return false;
        }
    }

    /**
     * Gets a specified shipment from the inventory
     *
     * @param item the item to search for
     * @return the shipment that is found
     * @throws ItemNotFoundException if the item is not found
     */
    public Shipment getShipmentFromInventory(Item item) throws ItemNotFoundException {
        for (Shipment shipment : inventory) {
            if (shipment.getItem().equals(item)) {
                return shipment;
            }
        }
        throw new ItemNotFoundException();
    }

    /**
     * Gets a specified shipment from a specified index of the inventory
     *
     * @param i the index to search
     * @return the shipment that is found
     */
    public Shipment getShipmentFromInventory(int i) {
        return this.inventory.get(i);
    }

    /**
     * Adds a shipment to the store, will create a new item or add on to a current item
     *
     * @param item        the item to add
     * @param orderAmount the amount to add
     */
    public void createInventoryOrder(Item item, int orderAmount) {
        try {
            Shipment selectedShipment = getShipmentFromInventory(item);
            selectedShipment.setAmount(selectedShipment.getAmount() + orderAmount);
        } catch (ItemNotFoundException e) {
            inventory.add(new Shipment(item, orderAmount));
        }
    }


    /**
     * Removes the specified amount of an item from the inventory
     *
     * @param item   the item to remove
     * @param amount the amount to remove
     * @throws OutOfStockException if there are not enough items to remove
     */
    public void removeFromInventory(Item item, int amount) throws OutOfStockException, ItemNotFoundException {
        Shipment selectedShipment = getShipmentFromInventory(item);
        if (selectedShipment.getAmount() >= amount) {
            selectedShipment.setAmount(selectedShipment.getAmount() - amount);
        } else {
            throw new OutOfStockException(item);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Shipment shipment : this.inventory) {
            stringBuilder.append(String.format("[%d] " + shipment.toString() + "\n", i));
        }
        return stringBuilder.toString();
    }


}
