package Project;

public class UnauthorizedException extends Exception {
    public UnauthorizedException() {
        super();
    }

    @Override
    public String getMessage() {
        return "401 Unauthorized";
    }
}
