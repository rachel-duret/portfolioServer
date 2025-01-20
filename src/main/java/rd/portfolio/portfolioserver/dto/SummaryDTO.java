package rd.portfolio.portfolioserver.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.model.Summary;

@Data
@Getter
@Setter
public class SummaryDTO {
    private Long id;
    private String value;
    private Long experienceId;

    public Summary convertToSummary() {
        Summary summary = new Summary();
        summary.setId(id);
        summary.setValue(value);
        return summary;
    }
}
