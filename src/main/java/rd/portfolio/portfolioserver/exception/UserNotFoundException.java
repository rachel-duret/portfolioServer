package rd.portfolio.portfolioserver.exception;

public class UserNotFoundException extends RuntimeException {
    private static final int HTTP_CODE = 404;
    private static final String DEFAULT_MESSAGE = "User not found";

    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}
