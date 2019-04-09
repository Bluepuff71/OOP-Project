package Project;

/**
 * An item is an entity that is sold in the store, it knows it's own quantity. Could also be called shipment.
 */
public class Item implements java.io.Serializable {

    /**
     * The item's name
     */
    private String name;

    /**
     * The item's description
     */
    private String description;

    /**
     * The item's price
     */
    private double price;

    /**
     * The quantity of the item
     */
    private int amount;

    /**
     * Creates a new item with no description and with quantity 1
     *
     * @param name  the name of the item
     * @param price the price of the item
     */
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        this.description = "";
        this.amount = 1;
    }

    /**
     * Creates a new item with quantity 1
     *
     * @param name        the name of the item
     * @param price       the price of the item
     * @param description the description of the item
     */
    public Item(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = 1;
    }

    /**
     * Creates an item with no description
     *
     * @param name   the name of the item
     * @param price  the price of the item
     * @param amount the quantity of the item
     */
    public Item(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.description = "";
        this.amount = amount;
    }

    /**
     * Creates a new item
     *
     * @param name        the name of the item
     * @param price       the price of the item
     * @param description the description of the item
     * @param amount      the quantity of the item
     */
    public Item(String name, double price, String description, int amount) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    //TODO setup proper toString to help whoever is doing interfaces
    @Override
    public String toString() {
        return "";
    }
}
