package rd.portfolio.portfolioserver.exception;

public class SocialAlreadyExistException extends RuntimeException {
    public static String DEFAULT_MESSAGE = "Skill already exist";

    public SocialAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }
}
