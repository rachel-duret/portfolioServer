package rd.portfolio.portfolioserver.params;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class ProjectParams {
    private Long userId;
    private String name;
    private String image;
    private String url;
    private String description;
}
