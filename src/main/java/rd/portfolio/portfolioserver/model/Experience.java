package rd.portfolio.portfolioserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.ExperienceDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Transient
    @Column(name = "technologies")
    private List<String> technologies;
    @Column(name = "image")
    private String image;
    @Column(name = "url")
    private String url;
    @Column(name = "description")
    private String description;
    @Column(name = "started_at")
    private Timestamp startedAt;
    @Column(name = "ended_at")
    private Timestamp endedAt;
    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.from(Instant.now());
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ExperienceDTO convertToDTO() {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        experienceDTO.setId(id);
        experienceDTO.setName(name);
        experienceDTO.setUrl(url);
        experienceDTO.setImage(image);
        return experienceDTO;
    }
}
