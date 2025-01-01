package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ExperienceDTO {
    Long id;
    String name;
    String image;
    String url;
}
