package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.model.User;

import java.util.List;

@Data
@Setter
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private ProfileDTO profile;
    private List<SocialDTO> socials;
    private List<SkillDTO> skills;
    private List<ProjectDTO> projects;
    private List<ExperienceDTO> experience;

    public User convertToUser(UserDTO userDTO) {

        return new User() {{
            setUsername(userDTO.getUsername());
            setEmail(userDTO.getEmail());
            setProfile(userDTO.getProfile().convertToProfile());
            setExperience(userDTO.getExperience());
        }};
    }

}
