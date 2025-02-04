package rd.portfolio.portfolioserver.service;

import rd.portfolio.portfolioserver.model.Profile;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.UpdateProfileParams;
import rd.portfolio.portfolioserver.params.UpdateUserParam;
import rd.portfolio.portfolioserver.params.UserParams;

public interface UserService {

    User getUserById(Long id);

    User getUserByLastnameAndFirstname(String lastname, String firstname);

    User createUser(UserParams userParam);

    User updateUser(Long id, UpdateUserParam updateUserParam);

    void deleteUser(Long id);

    boolean isUserExist(Long id);

    boolean isUserExist(String username);
    Profile updateUserProfile(Long id, UpdateProfileParams updateProfileParams);
}
