package rd.portfolio.portfolioserver.params;

import lombok.Data;

@Data
public class UserParams {
    private String username;
    private String password;
    private String email;
    private String profession;
    private String sex;
}
