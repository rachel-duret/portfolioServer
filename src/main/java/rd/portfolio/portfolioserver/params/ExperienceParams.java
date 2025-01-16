package rd.portfolio.portfolioserver.params;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import rd.portfolio.portfolioserver.dto.SkillDTO;
import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.model.Summary;

import java.util.List;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class ExperienceParams {
   private Long userId;
   private String name;
   private String company;
   private String image;
   private String url;
   private String description;
   private String startedAt;
   private String endedAt;
   private List<SkillDTO> technologies;
   private List<Summary> summaries;
}
