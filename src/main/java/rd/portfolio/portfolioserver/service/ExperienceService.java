package rd.portfolio.portfolioserver.service;

import rd.portfolio.portfolioserver.model.Experience;
import rd.portfolio.portfolioserver.model.Project;
import rd.portfolio.portfolioserver.params.ExperienceParams;
import rd.portfolio.portfolioserver.params.ProjectParams;

import java.util.List;

public interface ExperienceService {
    Experience save(ExperienceParams experienceParams);

    List<Experience> findAll();

    Experience findById(Long id);

    void delete(Long id);

    Experience update(Long id, ExperienceParams experienceParams);

    Boolean existsById(Long id);

    Boolean existsByName(String name);
}
