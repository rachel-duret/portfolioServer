package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.stylesheets.LinkStyle;
import rd.portfolio.portfolioserver.model.Experience;
import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.model.Summary;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Setter
@Getter
public class ExperienceDTO {
    private Long id;
    private String name;
    private String company;
    private String image;
    private String url;
    private String description;
    private List<SkillDTO> technologies;
    private List<SummaryDTO> summaries;
    private String startedAt;
    private String endedAt;

    public Experience convertToExperience() {
        Experience experience = new Experience();
        experience.setId(id);
        experience.setName(name);
        experience.setCompany(company);
        experience.setImage(image);
        experience.setUrl(url);
        experience.setDescription(description);
        experience.setTechnologies(technologies.stream().map(SkillDTO::convertToSkill).collect(Collectors.toList()));
        experience.setSummaries(summaries.stream().map(SummaryDTO::convertToSummary).collect(Collectors.toList()));
        experience.setStartedAt(Timestamp.valueOf(startedAt));
        experience.setEndedAt(Timestamp.valueOf(endedAt));
        return experience;
    }
}
