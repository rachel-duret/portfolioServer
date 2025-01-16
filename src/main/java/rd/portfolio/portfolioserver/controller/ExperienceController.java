package rd.portfolio.portfolioserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rd.portfolio.portfolioserver.dto.ExperienceDTO;
import rd.portfolio.portfolioserver.model.Experience;
import rd.portfolio.portfolioserver.params.ExperienceParams;
import rd.portfolio.portfolioserver.service.ExperienceService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/experiences")
public class ExperienceController {
    private final ExperienceService experienceService;

    @GetMapping("/{id}")
    public ResponseEntity<ExperienceDTO> getExperienceById(@PathVariable Long id) {
        Experience experience = experienceService.findById(id);
        return ResponseEntity.ok(experience.convertToDTO());
    }

    @GetMapping("")
    public ResponseEntity<List<ExperienceDTO>> getAllExperiences() {
        List<Experience> experiences = experienceService.findAll();
        return ResponseEntity.ok(experiences.stream().map(Experience::convertToDTO).toList());
    }

    @PostMapping("/experience")
    public ResponseEntity<ExperienceDTO> createExperience(@RequestBody ExperienceParams experienceParams) {
        Experience experience = experienceService.save(experienceParams);

        return ResponseEntity.ok(experience.convertToDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExperienceDTO> updateExperience(@PathVariable Long id, @RequestBody ExperienceParams experienceParams) {
        Experience experience = experienceService.update(id, experienceParams);
        return ResponseEntity.ok(experience.convertToDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        experienceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
