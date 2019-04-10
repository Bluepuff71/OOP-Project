package Project;

public class InvalidLoginException extends Exception {
    @Override
    public String getMessage() {
        return "Login is not valid";
    }
}
