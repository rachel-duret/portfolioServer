package rd.portfolio.portfolioserver.service;

import rd.portfolio.portfolioserver.dto.UserDTO;
import rd.portfolio.portfolioserver.model.User;

public interface UserService {

    User getUserById(Long id);
    User createUser(UserDTO userDTO);
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
