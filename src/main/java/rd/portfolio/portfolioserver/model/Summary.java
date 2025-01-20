package rd.portfolio.portfolioserver.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.SummaryDTO;

@Entity
@Getter
@Setter
@Table(name = "summaries", schema = "portfolio")
public class Summary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String value;

    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;

    public SummaryDTO convertToDTO() {
        SummaryDTO summaryDTO = new SummaryDTO();
        summaryDTO.setId(this.id);
        summaryDTO.setValue(this.value);
        return summaryDTO;
    }
}
