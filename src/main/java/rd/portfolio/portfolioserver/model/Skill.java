package rd.portfolio.portfolioserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.SkillDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "skills", schema = "portfolio")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.from(Instant.now());
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "technologies")
    private List<Experience> experiences;

    public SkillDTO convertToDTO() {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(id);
        skillDTO.setName(name);
        skillDTO.setUrl(url);
        skillDTO.setImage(image);
        return skillDTO;
    }
}
