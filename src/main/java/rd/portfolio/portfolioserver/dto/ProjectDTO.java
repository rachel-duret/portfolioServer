package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProjectDTO {
    Long id;
    String name;
    String image;
    String url;
}
