package project;

public class CreditLimitException extends Exception {
    @Override
    public String getMessage() {
        return "The credit limit was reached.";
    }
}
