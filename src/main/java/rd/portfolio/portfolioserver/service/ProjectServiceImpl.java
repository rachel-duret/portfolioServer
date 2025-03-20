package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
import rd.portfolio.portfolioserver.exception.ProjectAlreadyExistException;
import rd.portfolio.portfolioserver.exception.ProjectNotFoundException;
import rd.portfolio.portfolioserver.model.Project;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.ProjectParams;
import rd.portfolio.portfolioserver.repository.ProjectRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final SecurityUtil securityUtil;
    private final ProjectRepository projectRepository;

    @Override
    public Project save(ProjectParams projectParams) {
        this.validateProjectParams(projectParams);
        User user = this.securityUtil.ensureLoggedUser(projectParams.getUserId());
        boolean isExist = this.existsByName(projectParams.getName());
        if (isExist) {
            throw new ProjectAlreadyExistException();
        }
        this.validateProjectParams(projectParams);
        Project project = new Project();
        project.setUser(user);
        this.applyProject(projectParams, project);

        return projectRepository.save(project);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        Project project = this.projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        this.securityUtil.ensureLoggedUser(project.getUser().getId());
        projectRepository.deleteById(id);
    }

    @Override
    public Project update(Long id, ProjectParams projectParams) {
        this.securityUtil.ensureLoggedUser(projectParams.getUserId());
        Project project = this.projectRepository.findById(id).orElseThrow(ProjectNotFoundException::new);
        this.validateProjectParams(projectParams);
        project.setUpdatedAt(Timestamp.from(Instant.now()));
        this.applyProject(projectParams, project);
        return projectRepository.save(project);
    }

    @Override
    public Boolean existsById(Long id) {
        return projectRepository.existsById(id);
    }

    @Override
    public Boolean existsByName(String name) {
        return projectRepository.findByName(name).isPresent();
    }

    private void validateProjectParams(ProjectParams projectParams) {
        Map<String, String> errors = new HashMap<>();
        if (projectParams.getName() == null || projectParams.getName().isEmpty()) {
            errors.put("name", "Name cannot be empty");
        }
        if (projectParams.getImage() == null || projectParams.getImage().isEmpty()) {
            errors.put("image", "Image cannot be empty");
        }
        if (projectParams.getUrl() == null || projectParams.getUrl().isEmpty()) {
            errors.put("url", "Url cannot be empty");
        }
        if (!errors.isEmpty()) {
            throw new InvalidParameterException(errors);
        }
    }

    private void applyProject(ProjectParams projectParams, Project project) {
        project.setName(projectParams.getName());
        project.setImage(projectParams.getImage());
        project.setUrl(projectParams.getUrl());
        project.setDescription(projectParams.getDescription());
    }

}
