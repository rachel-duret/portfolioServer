package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.model.User;

@Data
@Setter
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;

    public User convertToUser(UserDTO userDTO) {

        return new User() {{
            setUsername(userDTO.getUsername());
            setPassword(userDTO.getPassword());
            setEmail(userDTO.getEmail());
        }};
    }

}
