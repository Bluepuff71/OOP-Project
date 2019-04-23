package project;

/**
 * Exception for handling when the credit limit of a card is exceeded
 *
 * @see Card
 */
public class CreditLimitException extends Exception {

    @Override
    public String getMessage() {
        return "The credit limit was reached.";
    }
}
