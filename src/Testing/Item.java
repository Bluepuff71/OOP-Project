package Testing;

public class Item implements java.io.Serializable {

    private String name;
    private double price;

    public Item(String name, double price){
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("-- %s --\n" +
                "Price: $%.2f", name, price);
    }
}
