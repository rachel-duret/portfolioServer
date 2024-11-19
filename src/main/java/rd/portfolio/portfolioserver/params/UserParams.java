package rd.portfolio.portfolioserver.params;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserParams {
    private String username;
    private String password;
    @Email(message = "Have to be a email format")
    private String email;
    private String profession;
    private String sex;
}
