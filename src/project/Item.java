package project;

/**
 * An item is an entity that is sold in the store
 */
public final class Item implements java.io.Serializable {

    /**
     * The item's name
     */
    private String name;

    /**
     * The item's price
     */
    private double price;


    /**
     * Creates a new item
     *
     * @param name        the name of the item
     * @param price       the price of the item
     */
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f", this.name, this.price);
    }
}
