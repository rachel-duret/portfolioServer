package rd.portfolio.portfolioserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.ProfileDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "phone")
    private String phone;
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ProfileDTO conventToDTO() {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(this.id);
        profileDTO.setPhone(this.getPhone());
        profileDTO.setImageUrl(this.getImageUrl());
        profileDTO.setAboutMe(this.getAboutMe());
        profileDTO.setProfession(this.getProfession());
        profileDTO.setSex(this.getSex());
        profileDTO.setBirthday(this.getBirthday());
        return profileDTO;
    }
}
