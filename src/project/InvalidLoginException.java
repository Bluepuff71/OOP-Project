package project;

/**
 * Exception for handling when attempting to access an account with incorrect credentials
 *
 * @see LoginManager
 * @see security.Hash
 */
public class InvalidLoginException extends Exception {
    @Override
    public String getMessage() {
        return "Login is not valid";
    }
}
