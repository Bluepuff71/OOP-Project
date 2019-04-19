package project;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager implements java.io.Serializable {

    /**
     * The inventory of the storefront
     */
    private List<Shipment> inventory;

    public InventoryManager() {
        inventory = new ArrayList<>();
    }

    /**
     * Creates an order request from a customers cart
     *
     * @param customer the customer that is creating the order
     * @return the order that is created
     * @throws InvalidCardException  if the customer's card number is not valid
     * @throws CreditLimitException  if the purchase would exceed the customer's credit limit
     */
    public Order createOrderRequest(Customer customer) throws InvalidCardException, CreditLimitException {
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
    public Shipment getShipmentFromInventory(Item item) throws ItemNotFoundException, NullPointerException {
        for (Shipment shipment : inventory) {
            if (shipment.getItem().getName().equals(item.getName())) {
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
     * @throws NullPointerException if the index returns null
     */
    public Shipment getShipmentFromInventory(int i) throws NullPointerException {
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
        } catch (ItemNotFoundException | NullPointerException e) {
            inventory.add(new Shipment(item, orderAmount));
        }
    }


    /**
     * Removes the specified amount of an item from the inventory (removes the item from the inventory if needed)
     *
     * @param item   the item to remove
     * @param amount the amount to remove
     * @throws OutOfStockException if there are not enough items to remove
     * @throws ItemNotFoundException if the item isn't found in the inventory
     */
    public void removeFromInventory(Item item, int amount) throws OutOfStockException, ItemNotFoundException {
        Shipment selectedShipment = getShipmentFromInventory(item);
        if (selectedShipment.getAmount() > amount) {
            selectedShipment.setAmount(selectedShipment.getAmount() - amount);
        } else if (selectedShipment.getAmount() == amount) {
            inventory.remove(selectedShipment);
        } else {
            throw new OutOfStockException(item);
        }
    }

    public int getInventorySize() {
        return inventory.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (Shipment shipment : this.inventory) {
            stringBuilder.append(String.format("[%d] " + shipment.toString() + "\n", i));
            i++;
        }
        return stringBuilder.toString();
    }


}
