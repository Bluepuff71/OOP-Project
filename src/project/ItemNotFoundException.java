package Project;

public class ItemNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "The requested item was not found";
    }
}
