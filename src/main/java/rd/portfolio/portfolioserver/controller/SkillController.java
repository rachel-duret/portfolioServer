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
import rd.portfolio.portfolioserver.dto.SkillDTO;
import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.params.SkillParams;
import rd.portfolio.portfolioserver.service.SkillService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable Long id) {
        Skill skill = skillService.findById(id);
        return ResponseEntity.ok(skill.convertToDTO());
    }

    @GetMapping("")
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        List<Skill> skills = skillService.findAll();
        return ResponseEntity.ok(skills.stream().map(Skill::convertToDTO).toList());
    }

    @PostMapping("")
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillParams skillParams) {
        Skill skill = skillService.save(skillParams);

        return ResponseEntity.ok(skill.convertToDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable Long id, @RequestBody SkillParams skillParams) {
        Skill skill = skillService.update(id, skillParams);
        return ResponseEntity.ok(skill.convertToDTO());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
