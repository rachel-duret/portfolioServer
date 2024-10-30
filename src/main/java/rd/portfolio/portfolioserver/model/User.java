package rd.portfolio.portfolioserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.UserDTO;

import java.time.LocalDate;

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
    private String role;
    @Column(name = "imageUrl")
    private String imageUrl;
    @Column(name = "about_me")
    private String aboutMe;
    @Column(name = "profession", nullable = false)
    private String profession;
    @Column(name = "sex", nullable = false)
    private String sex;
    @Column(name = "created_at")
    private long createdAt = System.currentTimeMillis();
    @Column(name = "updated_at")
    private long updatedAt;

    public UserDTO conventToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setRole(user.getRole());
        userDTO.setImageUrl(user.getImageUrl());
        userDTO.setAboutMe(user.getAboutMe());
        userDTO.setProfession(user.getProfession());
        return userDTO;
    }
}
