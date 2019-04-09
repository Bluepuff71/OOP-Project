package Project;

import java.util.HashMap;

public class Storefront implements java.io.Serializable {

    /**
     * The inventory of the storefront
     */
    private HashMap<String, Shipment> inventory = new HashMap<>();

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
            if (!inventory.containsKey(shipment.getItem().getName().toLowerCase())) {
                throw new ItemNotFoundException();
            }
        }
        return new Order(customer.getUsername(), customer.getCart(), Bank.getPurchaseAuthorizationNumber(customer.getCard(), customer.getCartTotalCost()));
    }

    /**
     * Adds shipments to the store, will create a new item or add on to a current item
     *
     * @param item        the item to add
     * @param orderAmount the amount to add
     */
    public void createInventoryOrder(Item item, int orderAmount) {
        if (inventory.containsKey(item.getName().toLowerCase())) {
            Shipment selectedShipment = inventory.get(item.getName().toLowerCase());
            selectedShipment.setAmount(selectedShipment.getAmount() + orderAmount);
        } else {
            inventory.put(item.getName().toLowerCase(), new Shipment(item, orderAmount));
        }
    }

    /**
     * Checks the inventory for a specified amount of an item
     *
     * @param item   the item to check
     * @param amount the amount to check
     * @return true if there is enough supply in the inventory
     */
    public boolean inventoryCheck(Item item, int amount) {
        return inventory.get(item.getName().toLowerCase()).getAmount() >= amount;
    }

    public void removeFromInventory(Item item, int amount) throws OutOfStockException {
        Shipment selectedItem = inventory.get(item.getName().toLowerCase());
        if (selectedItem.getAmount() >= amount) {
            selectedItem.setAmount(selectedItem.getAmount() - amount);
        } else {
            throw new OutOfStockException(item);
        }
    }

    @Override
    public String toString() {
        return "Display storefront";
    }
}