package rd.portfolio.portfolioserver.service;

import rd.portfolio.portfolioserver.model.Social;
import rd.portfolio.portfolioserver.params.SocialParams;

import java.util.List;

public interface SocialService {
    Social createSocial(SocialParams socialParams);

    Social updateSocial(Long id, SocialParams socialParams);

    Social getSocialById(Long id);

    List<Social> getAllSocial();

    void deleteSocialById(Long id);

    boolean isSocialExist(Long id);
}
