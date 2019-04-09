package Project;

/**
 * An item is an entity that is sold in the store
 */
public class Item implements java.io.Serializable {

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
     * @param description the description of the item
     */
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //TODO setup proper toString to help whoever is doing interfaces
    @Override
    public String toString() {
        return "";
    }
}
