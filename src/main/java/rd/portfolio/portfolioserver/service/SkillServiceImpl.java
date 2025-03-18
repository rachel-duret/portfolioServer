package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
import rd.portfolio.portfolioserver.exception.SkillAlreadyExistException;
import rd.portfolio.portfolioserver.exception.SkillNotFoundException;
import rd.portfolio.portfolioserver.model.Experience;
import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.SkillParams;
import rd.portfolio.portfolioserver.repository.SkillRepository;
import rd.portfolio.portfolioserver.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final SecurityUtil securityUtil;
    private final ExperienceService experienceService;

    @Override
    public Skill save(SkillParams skillParams) {
        User user = this.securityUtil.ensureLoggedUser(skillParams.getUserId());
        boolean isExist = this.existsByName(skillParams.getName());
        if (isExist) {
            throw new SkillAlreadyExistException();
        }
        this.validateSkillParams(skillParams);

        Skill skill = new Skill();
        skill.setUser(user);
        this.applySkill(skillParams, skill);

        return skillRepository.save(skill);
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Override
    public Skill findById(Long id) {
        return skillRepository.findById(id).orElseThrow();
    }

    @Override
    public void delete(Long id) {
        Skill skill = this.skillRepository.findById(id).orElseThrow(SkillNotFoundException::new);
        this.securityUtil.ensureLoggedUser(skill.getUser().getId());
        List<Experience> experiences = skill.getExperiences();
        experiences.forEach(experience -> {
            experience.getTechnologies().remove(skill);
        });

        skillRepository.deleteById(id);
    }

    @Override
    public Skill update(Long id, SkillParams skillParams) {
        this.securityUtil.ensureLoggedUser(skillParams.getUserId());
        this.validateSkillParams(skillParams);
        Skill skill = this.skillRepository.findById(id).orElseThrow(SkillNotFoundException::new);
        skill.setUpdatedAt(Timestamp.from(Instant.now()));
        this.applySkill(skillParams, skill);
        return skillRepository.save(skill);
    }

    @Override
    public Boolean existsById(Long id) {
        return skillRepository.existsById(id);
    }

    @Override
    public Boolean existsByName(String name) {
        return skillRepository.findByName(name).isPresent();
    }

    private void validateSkillParams(SkillParams skillParams) {
        Map<String, String> errors = new HashMap<>();
        if (skillParams.getName() == null || skillParams.getName().isEmpty()) {
            errors.put("name", "Name cannot be empty");
        }
        if (skillParams.getImage() == null || skillParams.getImage().isEmpty()) {
            errors.put("image", "Image cannot be empty");
        }
        if (skillParams.getUrl() == null || skillParams.getUrl().isEmpty()) {
            errors.put("url", "Url cannot be empty");
        }
        if (!errors.isEmpty()) {
            throw new InvalidParameterException(errors);
        }
    }

    private void applySkill(SkillParams skillParams, Skill skill) {
        skill.setName(skillParams.getName());
        skill.setImage(skillParams.getImage());
        skill.setUrl(skillParams.getUrl());
        Experience experience = new Experience();
        experience.setId(1L);

    }

}
