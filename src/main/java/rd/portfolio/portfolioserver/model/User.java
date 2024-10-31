package rd.portfolio.portfolioserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.RoleType;
import rd.portfolio.portfolioserver.dto.UserDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
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
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "phone")
    private String phone;
    @Column(name = "role", nullable = false)
    private RoleType role;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "about_me")
    private String aboutMe;
    @Column(name = "profession", nullable = false)
    private String profession;
    @Column(name = "sex", nullable = false)
    private String sex;
    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.from(Instant.now());
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToMany
    @JoinTable(name = "skill", joinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;

    public UserDTO conventToDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(this.getUsername());
        userDTO.setPassword(this.getPassword());
        userDTO.setEmail(this.getEmail());
        userDTO.setPhone(this.getPhone());
        userDTO.setRole(this.getRole());
        userDTO.setImageUrl(this.getImageUrl());
        userDTO.setAboutMe(this.getAboutMe());
        userDTO.setProfession(this.getProfession());
        return userDTO;
    }
}
