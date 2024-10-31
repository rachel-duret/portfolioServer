package rd.portfolio.portfolioserver.exception;

public class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException() {
    }

    public SkillNotFoundException(String message) {
        super(message);
    }
}
