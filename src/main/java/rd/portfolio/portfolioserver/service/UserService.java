package rd.portfolio.portfolioserver.service;

import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.UserParams;

public interface UserService {

    User getUserById(Long id);

    User getUserByName(String name);

    User createUser(UserParams userParam);

    User updateUser(Long id, UserParams userParams);

    void deleteUser(Long id);

    boolean isUserExist(Long id);

    boolean isUserExist(String username);
}
