package rd.portfolio.portfolioserver.exception;

import java.util.Map;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException() {
        super();
    }

    public InvalidParameterException(Map<String, String> message) {
        super(message.toString());
    }

}
