package project;

public class InvalidAccountTypeException extends Exception {
    @Override
    public String getMessage() {
        return "The account type is incorrect";
    }
}
