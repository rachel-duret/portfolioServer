package rd.portfolio.portfolioserver.exception;

public class ProjectAlreadyExistException extends RuntimeException {
    public static String DEFAULT_MESSAGE = "Project already exist";

    public ProjectAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }
}
