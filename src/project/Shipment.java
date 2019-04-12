package project;

public final class Shipment implements java.io.Serializable {

    private Item item;

    private int amount;


    public Shipment(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("%s - $%.2f", item.getName(), item.getPrice());
    }
}
