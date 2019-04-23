package project;

/**
 * Exception for handling when the card info is invalid
 *
 * @see Card
 */
public class InvalidCardException extends Exception {

    public InvalidCardException(String message) {
        super(message);
    }
}
