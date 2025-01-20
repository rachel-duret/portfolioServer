package rd.portfolio.portfolioserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.ExperienceDTO;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "company")
    private String company;
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

   @OneToMany(mappedBy = "experience", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Summary> summaries = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "technologies_skills",
            joinColumns = @JoinColumn(name = "experience_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")

    )
    private List<Skill> technologies = new ArrayList<>();

    public ExperienceDTO convertToDTO() {
        ExperienceDTO experienceDTO = new ExperienceDTO();
        experienceDTO.setId(id);
        experienceDTO.setName(name);
        experienceDTO.setUrl(url);
        experienceDTO.setImage(image);
        experienceDTO.setDescription(description);
        experienceDTO.setCompany(company);
        experienceDTO.setTechnologies(technologies.stream().map(Skill::convertToDTO).collect(Collectors.toList()));
        experienceDTO.setStartedAt(startedAt.toString());
        experienceDTO.setEndedAt(endedAt.toString());
        experienceDTO.setSummaries(summaries.stream().map(Summary::convertToDTO).collect(Collectors.toList()));
        return experienceDTO;
    }
}
