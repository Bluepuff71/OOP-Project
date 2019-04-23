package project;

import java.util.ArrayList;
import java.util.List;

/**
 * A customer account for standard clients
 *
 * @see Account
 */
public final class Customer extends Account implements java.io.Serializable {

    /**
     * The debit/credit card information for the customer
     *
     * @see Card
     */
    private Card card;

    /**
     * The 10 digit phone number of the customer
     */
    private String phoneNumber;

    /**
     * The address of the user (1234 Street Ln)
     */
    private String address;

    /**
     * The cart of items the user has selected
     *
     * @see Shipment
     */
    private List<Shipment> cart;

    /**
     * The orders that the customer has created
     *
     * @see Order
     */
    private List<Order> orders;

    /**
     * Creates a new customer account with an empty cart
     *
     * @param username  the username of the customer
     * @param plainText the plaintext password of the customer
     * @param phoneNumber the 10 digit phone number of the customer
     * @param address the address of the user (1234 Street Ln)
     * @param card      the card information of the customer
     */
    public Customer(String username, String plainText, String phoneNumber, String address, Card card) {
        super(username, plainText);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.card = card;
        this.cart = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    /**
     * Returns the customer's card
     *
     * @return the card of the customer
     * @see Card
     */
    public Card getCard() {
        return card;
    }

    /**
     * The cart of the customer
     *
     * @return the cart of the customer
     * @see Shipment
     */
    public List<Shipment> getCart() {
        return cart;
    }

    /**
     * Adds an item to the cart (additive)
     *
     * @param item   the item to add
     * @param amount the amount to add
     * @see Item
     */
    public void addToCart(Item item, int amount) {
        for (Shipment shipment : cart) {
            if (shipment.getItem().getName().equals(item.getName())) {
                shipment.setAmount(shipment.getAmount() + amount);
                return;
            }
        }
        cart.add(new Shipment(item, amount));
    }

    /**
     * Fully removes an item from the cart
     *
     * @param item the item to remove
     * @see Item
     */
    public void removeFromCart(Item item) {
        for (Shipment shipment : cart) {
            if (shipment.getItem().getName().equals(item.getName())) {
                cart.remove(shipment);
                break;
            }
        }
    }

    /**
     * Removes a specified number of an item from the cart
     *
     * @param item   the item to remove
     * @param amount the amount to remove
     * @see Item
     */
    public void removeFromCart(Item item, int amount) {
        for (Shipment cartItem : cart) {
            if (cartItem.getItem().getName().equals(item.getName())) {
                if (cartItem.getAmount() <= amount) {
                    cart.remove(cartItem);
                } else {
                    cartItem.setAmount(cartItem.getAmount() - amount);
                }
                break;
            }
        }
    }

    /**
     * Gets the total cost of the cart
     *
     * @return the total cost of the cart
     */
    public double getCartTotalCost() {
        double totalCost = 0;
        for (Shipment shipment : cart) {
            totalCost += (shipment.getItem().getPrice() * shipment.getAmount());
        }
        return totalCost;
    }

    /**
     * Gets a list of all the orders that the customer has placed
     *
     * @return a list of all the orders the customer has placed
     * @see Order
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Adds an order to the customer's order list
     *
     * @param order the order to add
     * @see Order
     */
    public void addOrder(Order order) {
        this.orders.add(order);
        Supplier.getDeliveryOrderList().add(order);
    }

    /**
     * Removes an order from the customer's order list
     *
     * @param order the order to remove
     * @see Order
     */
    public void removeOrder(Order order) {
        this.orders.remove(order);
    }
}
