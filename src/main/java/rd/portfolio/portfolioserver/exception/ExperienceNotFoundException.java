package rd.portfolio.portfolioserver.exception;

public class ExperienceNotFoundException extends RuntimeException {
    public ExperienceNotFoundException() {
    }

    public ExperienceNotFoundException(String message) {
        super(message);
    }
}
