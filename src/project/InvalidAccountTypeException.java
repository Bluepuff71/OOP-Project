package project;

/**
 * Exception for handling when trying to access non-existent properties of an account
 *
 * @see Account
 */
public class InvalidAccountTypeException extends Exception {
    @Override
    public String getMessage() {
        return "The account type is incorrect";
    }
}
