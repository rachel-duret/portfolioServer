package rd.portfolio.portfolioserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rd.portfolio.portfolioserver.dto.ProjectDTO;
import rd.portfolio.portfolioserver.model.Project;
import rd.portfolio.portfolioserver.params.ProjectParams;
import rd.portfolio.portfolioserver.service.ProjectService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        return ResponseEntity.ok(project.convertToDTO());
    }

    @GetMapping("")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<Project> projects = projectService.findAll();
        return ResponseEntity.ok(projects.stream().map(Project::convertToDTO).toList());
    }

    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectParams projectParams) {
        Project project = projectService.save(projectParams);

        return ResponseEntity.ok(project.convertToDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectParams projectParams) {
        Project project = projectService.update(id, projectParams);
        return ResponseEntity.ok(project.convertToDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
