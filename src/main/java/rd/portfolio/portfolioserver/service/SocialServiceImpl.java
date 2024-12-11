package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
import rd.portfolio.portfolioserver.exception.SocialAlreadyExistException;
import rd.portfolio.portfolioserver.exception.SocialNotFoundException;
import rd.portfolio.portfolioserver.model.Social;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.SocialParams;
import rd.portfolio.portfolioserver.repository.SocialRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SocialServiceImpl implements SocialService {
    private final SecurityUtil securityUtil;
    private final SocialRepository socialRepository;

    @Override
    public Social createSocial(SocialParams socialParams) {
        User user = this.securityUtil.ensureLoggedUser(socialParams.getUserId());
        if (this.socialRepository.existsByName(socialParams.getName())) {
            throw new SocialAlreadyExistException();
        }
        Social social = new Social();
        social.setUser(user);
        this.applySocial(socialParams, social);

        return this.socialRepository.save(social);

    }

    @Override
    public Social updateSocial(Long id, SocialParams socialParams) {
        Social social = this.getSocialById(id);
        this.validateSocialParams(socialParams);
        this.applySocial(socialParams, social);
        social.setUpdatedAt(Timestamp.from(Instant.now()));

        return this.socialRepository.save(social);
    }

    @Override
    public Social getSocialById(Long id) {

        return this.socialRepository.findById(id).orElseThrow(SocialNotFoundException::new);
    }

    @Override
    public List<Social> getAllSocialByUserId(Long userId) {
        return this.socialRepository.findAll();
    }

    @Override
    public void deleteSocialById(Long id) {
        if (!this.socialRepository.existsById(id)) {
            throw new SocialNotFoundException();
        }
        this.socialRepository.deleteById(id);

    }

    @Override
    public boolean isSocialExist(Long id) {
        return this.socialRepository.existsById(id);
    }

    private void validateSocialParams(SocialParams socialParams) {
        Map<String, String> errors = new HashMap<>();
        if (socialParams.getName().isBlank()) {
            errors.put("name", "cannot be empty");
        }
        if (socialParams.getUrl().isBlank()) {
            errors.put("url", "cannot be empty");
        }
        if (socialParams.getUrl().isBlank()) {
            errors.put("url", "cannot be empty");
        }

        if (!errors.isEmpty()) {
            throw new InvalidParameterException(errors);
        }
    }

    private void applySocial(SocialParams socialParams, Social social) {
        social.setName(socialParams.getName());
        social.setUrl(socialParams.getUrl());
        social.setImageUrl(socialParams.getImageUrl());
    }

}
