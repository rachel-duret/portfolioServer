package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.model.User;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private RoleType role;
    private String imageUrl;
    private String aboutMe;
    private String profession;
    private String sex;
    private LocalDate birthday;

    public User convertToUser(UserDTO userDTO) {

        return new User() {{
            setUsername(userDTO.getUsername());
            setPassword(userDTO.getPassword());
            setEmail(userDTO.getEmail());
            //            setPhone(userDTO.getPhone());
            setRole(userDTO.getRole());
            //            setImageUrl(userDTO.getImageUrl());
            //            setAboutMe(userDTO.getAboutMe());
            setProfession(userDTO.getProfession());
            setSex(userDTO.getSex());
            //            setBirthday(userDTO.birthday);
        }};
    }

}
