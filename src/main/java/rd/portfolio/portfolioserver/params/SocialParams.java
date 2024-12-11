package rd.portfolio.portfolioserver.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class SocialParams {
    private Long userId;
    private String name;
    private String url;
    private String imageUrl;
}
