package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import rd.portfolio.portfolioserver.model.User;

import java.time.LocalDate;

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
    private LocalDate birthday;

    public User convertToUser(UserDTO userDTO) {

        return new User(){{
            setUsername(userDTO.getUsername());
            setPassword(userDTO.getPassword());
            setEmail(userDTO.getEmail());
            setPhone(userDTO.getPhone());
            setRole(userDTO.getRole());
            setImageUrl(userDTO.getImageUrl());
            setAboutMe(userDTO.getAboutMe());
            setProfession(userDTO.getProfession());
            setBirthday(userDTO.birthday);
        }};
    }

}
