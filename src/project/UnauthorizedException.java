package project;

/**
 * Exception for handling when trying access a piece of an account with without logging in
 */
public class UnauthorizedException extends Exception {
    @Override
    public String getMessage() {
        return "401 Unauthorized";
    }
}
