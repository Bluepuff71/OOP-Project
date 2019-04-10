package Project;

public class NoAccountFoundException extends Exception {
    @Override
    public String getMessage() {
        return "No account was found with the requested name";
    }
}
