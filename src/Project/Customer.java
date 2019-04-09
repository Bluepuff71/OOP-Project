package Project;

import java.util.ArrayList;

/**
 * A customer account for standard clients
 */
public class Customer extends Account implements java.io.Serializable {

    /**
     * The debit/credit card information for the customer
     */
    private Card card;

    /**
     * A list of all the items the Customer has added to their cart
     */
    private ArrayList<Item> cart;

    /**
     * Creates a new customer account with an empty cart
     *
     * @param username  the username of the customer
     * @param plainText the plaintext password of the customer
     * @param card      the card information of the customer
     */
    public Customer(String username, String plainText, Card card) {
        super(username, plainText);
        this.card = card;
        this.cart = null;
    }
}
