package rd.portfolio.portfolioserver.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.UserDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "portfolio")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "first_name", nullable = false, unique = true)
    private String firstName;
    @Column(name = "last_name", nullable = false, unique = true)
    private String lastName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.from(Instant.now());
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Social> socials;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hobby> hobbies;
    public UserDTO conventToDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(this.id);
        userDTO.setUsername(this.getUsername());
        userDTO.setEmail(this.getEmail());
        if (this.profile !=null){
        userDTO.setProfile(this.profile.conventToDTO());
        }
        userDTO.setSkills(this.getSkills().stream().map(Skill::convertToDTO).toList());
        userDTO.setSocials(this.getSocials().stream().map(Social::convertToDTO).toList());
        userDTO.setProjects(this.getProjects().stream().map(Project::convertToDTO).toList());
        userDTO.setExperiences(this.getExperiences().stream().map(Experience::convertToDTO).toList());
        userDTO.setHobbies(this.getHobbies().stream().map(Hobby::convertToDTO).toList());
        return userDTO;
    }
}
