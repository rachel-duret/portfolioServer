package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.dto.SkillDTO;
import rd.portfolio.portfolioserver.exception.*;
import rd.portfolio.portfolioserver.model.Experience;
import rd.portfolio.portfolioserver.model.Project;
import rd.portfolio.portfolioserver.model.Skill;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.ExperienceParams;
import rd.portfolio.portfolioserver.params.ProjectParams;
import rd.portfolio.portfolioserver.repository.ExperienceRepository;
import rd.portfolio.portfolioserver.repository.ProjectRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {
    private final SecurityUtil securityUtil;
    private final ExperienceRepository experienceRepository;


    @Override
    public Experience save(ExperienceParams experienceParams) {
        User user = this.securityUtil.ensureLoggedUser(experienceParams.getUserId());
        boolean isExist = this.existsByName(experienceParams.getName());
        if (isExist) {
            throw new ExperienceAlreadyExistException();
        }
        this.validateExperienceParams(experienceParams);
        Experience experience = new Experience();
        this.applyExperience(experienceParams, experience);
        experience.setUser(user);
        return experienceRepository.save(experience);
    }

    @Override
    public List<Experience> findAll() {
        return this.experienceRepository.findAll();
    }

    @Override
    public Experience findById(Long id) {
        return this.experienceRepository.findById(id).orElseThrow(ExperienceNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
    Experience experience = this.findById(id);
    this.securityUtil.ensureLoggedUser(experience.getUser().getId());
    experience.getTechnologies().removeAll(experience.getTechnologies());
    this.experienceRepository.delete(experience);
    }

    @Override
    public Experience update(Long id, ExperienceParams experienceParams) {
        this.securityUtil.ensureLoggedUser(experienceParams.getUserId());
        Experience experience = this.findById(id);
        this.validateExperienceParams(experienceParams);
        experience.setUpdatedAt(Timestamp.from(Instant.now()));
        this.applyExperience(experienceParams, experience);
        return experience;
    }

    private void applyExperience(ExperienceParams experienceParams, Experience experience) {
        experience.setName(experienceParams.getName());
        experience.setCompany(experienceParams.getCompany());
        experience.setImage(experienceParams.getImage());
        experience.setUrl(experienceParams.getUrl());
        experience.setDescription(experienceParams.getDescription());
        experience.setStartedAt(Timestamp.from(Instant.parse(experienceParams.getStartedAt())));
        experience.setEndedAt(Timestamp.from(Instant.parse(experienceParams.getEndedAt())));
        List<Skill> skills=experienceParams.getTechnologies().stream().map(SkillDTO::convertToSkill).toList();
        experience.setSummaries(experienceParams.getSummaries());
        experience.setTechnologies(skills);

    }

    private void validateExperienceParams(ExperienceParams experienceParams) {
        Map<String, String> errors = new HashMap<>();
        if (experienceParams.getName() == null || experienceParams.getName().isEmpty()) {
            errors.put("name", "Name cannot be empty");
        }
        if (experienceParams.getCompany() == null || experienceParams.getCompany().isEmpty()) {
            errors.put("company", "company name cannot be empty");
        }
        if (experienceParams.getImage() == null || experienceParams.getImage().isEmpty()) {
            errors.put("image", "Image cannot be empty");
        }
        if (experienceParams.getUrl() == null || experienceParams.getUrl().isEmpty()) {
            errors.put("url", "Url cannot be empty");
        }
        if (experienceParams.getStartedAt() == null || experienceParams.getStartedAt().isEmpty()) {
            errors.put("started time", "Started time cannot be empty");
        }
        if (experienceParams.getEndedAt() == null || experienceParams.getEndedAt().isEmpty()) {
            errors.put("end time", "Ended time cannot be empty");
        }
        if (!errors.isEmpty()) {
            throw new InvalidParameterException(errors);
        }
    }

    @Override
    public Boolean existsById(Long id) {
        return this.experienceRepository.existsById(id);
    }

    @Override
    public Boolean existsByName(String name) {
        return this.experienceRepository.findByName(name).isPresent();
    }
}
