package rd.portfolio.portfolioserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.HobbyDTO;
import rd.portfolio.portfolioserver.dto.SummaryDTO;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "hobbies", schema = "portfolio")
public class Hobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "created_at")
    private Timestamp createdAt = Timestamp.from(Instant.now());
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public HobbyDTO convertToDTO() {
        HobbyDTO hobbyDTO = new HobbyDTO();
        hobbyDTO.setId(this.id);
        hobbyDTO.setName(this.name);
        hobbyDTO.setImageUrl(this.imageUrl);
        return hobbyDTO;
    }
}
