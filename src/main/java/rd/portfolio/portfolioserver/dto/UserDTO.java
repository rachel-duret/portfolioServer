package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Setter
@Getter
public class UserDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private ProfileDTO profile;
    private List<SocialDTO> socials;
    private List<SkillDTO> skills;
    private List<ProjectDTO> projects;
    private List<ExperienceDTO> experiences;
    private List<HobbyDTO> hobbies;

    public User convertToUser(UserDTO userDTO) {

        return new User() {{
            setUsername(userDTO.getUsername());
            setFirstname(userDTO.getFirstname());
            setLastname(userDTO.getLastname());
            setEmail(userDTO.getEmail());
            setProfile(userDTO.getProfile().convertToProfile());
            setExperiences(userDTO.getExperiences().stream().map(ExperienceDTO::convertToExperience).collect(Collectors.toList()));
            setHobbies(userDTO.getHobbies().stream().map(HobbyDTO::convertToHobby).collect(Collectors.toList()));
        }};
    }

}
