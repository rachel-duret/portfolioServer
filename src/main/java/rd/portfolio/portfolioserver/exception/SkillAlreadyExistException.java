package rd.portfolio.portfolioserver.exception;

public class SkillAlreadyExistException extends RuntimeException {
    public static String DEFAULT_MESSAGE = "Skill already exist";

    public SkillAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }
}
