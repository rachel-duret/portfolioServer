package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SocialDTO {
    private Long id;
    private String name;
    private String image;
    private String url;
}
