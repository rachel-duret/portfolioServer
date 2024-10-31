package rd.portfolio.portfolioserver.params;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Getter
@Setter
public class SkillParams {
    String name;
    String image;
    String url;
}
