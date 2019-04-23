package project;

/**
 * A class that contains an item with an amount of the item
 */
public final class Shipment implements java.io.Serializable {

    /**
     * The item of the shipment
     */
    private Item item;

    /**
     * The amount of the item
     */
    private int amount;

    /**
     * Creates a new shipment
     *
     * @param item   the item of the shipment
     * @param amount the amount of the item
     */
    public Shipment(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    /**
     * Returns the item that the shipment contains
     * @return the item that the shipment contains
     */
    public Item getItem() {
        return item;
    }

    /**
     * Returns the amount of the item in the shipment
     * @return the amount of the item in the shipment
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amount of items in the shipment
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * {@inheritDoc}
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s - $%.2f", item.getName(), item.getPrice());
    }
}
