package project;

/**
 * Exception for handling when trying to access an item that doesn't exist
 *
 * @see Item
 */
public class ItemNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "The requested item was not found";
    }
}
