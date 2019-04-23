package project;

/**
 * Exception for handling when trying to access an account that doesn't exist
 *
 * @see Account
 */
public class NoAccountFoundException extends Exception {
    @Override
    public String getMessage() {
        return "No account was found with the requested name";
    }
}
