package rd.portfolio.portfolioserver.exception;

public class ExperienceAlreadyExistException extends RuntimeException {
    public static String DEFAULT_MESSAGE = "Project already exist";

    public ExperienceAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }
}
