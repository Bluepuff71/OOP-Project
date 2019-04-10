package Project;

public class InvalidAccountTypeException extends Exception {
    @Override
    public String getMessage() {
        return "The account type is incorrect";
    }
}
