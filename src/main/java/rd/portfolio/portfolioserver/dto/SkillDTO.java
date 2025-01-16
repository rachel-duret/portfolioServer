package rd.portfolio.portfolioserver.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rd.portfolio.portfolioserver.model.Skill;

@Data
@Setter
@Getter
public class SkillDTO {
    Long id;
    String name;
    String image;
    String url;

    public Skill convertToSkill() {
        Skill skill = new Skill();
        skill.setName(name);
        skill.setImage(image);
        skill.setUrl(url);
        skill.setId(id);
        return skill;
    }
}
