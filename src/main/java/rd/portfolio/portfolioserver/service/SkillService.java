package rd.portfolio.portfolioserver.service;

import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.params.SkillParams;

import java.util.List;

public interface SkillService {

    Skill save(SkillParams skillParams);

    List<Skill> findAll();

    Skill findById(Long id);

    void delete(Long id);

    Skill update(Long id, SkillParams skillParams);

    Boolean existsById(Long id);

    Boolean existsByName(String name);
}
