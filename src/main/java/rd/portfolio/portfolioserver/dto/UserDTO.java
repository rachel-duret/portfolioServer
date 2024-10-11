package rd.portfolio.portfolioserver.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private String imageUrl;
    private String aboutMe;
    private String profession;
    private String sex;

}
