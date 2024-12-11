package rd.portfolio.portfolioserver.service;

import rd.portfolio.portfolioserver.model.Project;
import rd.portfolio.portfolioserver.params.ProjectParams;

import java.util.List;

public interface ProjectService {

    Project save(ProjectParams projectParams);

    List<Project> findAll();

    Project findById(Long id);

    void delete(Long id);

    Project update(Long id, ProjectParams projectParams);

    Boolean existsById(Long id);

    Boolean existsByName(String name);
}
