package Project;

public class OutOfStockException extends Exception {
    private Item item;

    public OutOfStockException(Item item) {
        super();
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String getMessage() {
        return "Requested amount is not available";
    }
}
