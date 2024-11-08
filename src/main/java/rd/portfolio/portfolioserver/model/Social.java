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
import rd.portfolio.portfolioserver.dto.SocialDTO;

@Getter
@Setter
@Entity
@Table(name = "social", schema = "portfolio")
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "url")
    private String url;
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public SocialDTO convertToDTO() {
        SocialDTO socialDTO = new SocialDTO();
        socialDTO.setId(this.id);
        socialDTO.setName(this.name);
        socialDTO.setUrl(this.url);
        return socialDTO;
    }

}
