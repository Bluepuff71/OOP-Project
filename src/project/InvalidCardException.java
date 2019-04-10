package project;

public class InvalidCardException extends Exception {
    @Override
    public String getMessage() {
        return "The card is invalid";
    }
}
