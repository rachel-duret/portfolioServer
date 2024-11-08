package rd.portfolio.portfolioserver.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.SkillDTO;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public SkillDTO convertToDTO() {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setId(id);
        skillDTO.setName(name);
        skillDTO.setUrl(url);
        return skillDTO;
    }
}
