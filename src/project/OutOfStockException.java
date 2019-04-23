package project;

/**
 * Exception for handling when trying to reserve an item that doesn't have enough stock left
 */
public class OutOfStockException extends Exception {
    /**
     * The item that is out of stock
     */
    private Item item;

    /**
     * Creates a new instance of the exception
     *
     * @param item the item that is out of stock
     */
    public OutOfStockException(Item item) {
        super();
        this.item = item;
    }

    /**
     * Returns the item that is out of stock
     * @return the item that is out of stock
     */
    public Item getItem() {
        return item;
    }

    @Override
    public String getMessage() {
        return "Requested amount is not available";
    }
}
