package rd.portfolio.portfolioserver.exception;

public class HobbyNotFoundException extends RuntimeException {
    public HobbyNotFoundException() {
    }

    public HobbyNotFoundException(String message) {
        super(message);
    }
}
