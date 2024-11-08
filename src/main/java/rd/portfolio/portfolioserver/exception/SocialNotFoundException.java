package rd.portfolio.portfolioserver.exception;

public class SocialNotFoundException extends RuntimeException {
    private static final int HTTP_CODE = 404;
    private static final String DEFAULT_MESSAGE = "Social not found";

    public SocialNotFoundException() {
        super();
    }

    public SocialNotFoundException(String message) {
        super(message);
    }
}
