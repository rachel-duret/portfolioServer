package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.dto.UserDTO;
import rd.portfolio.portfolioserver.exception.UserAlreadyExistException;
import rd.portfolio.portfolioserver.exception.UserNotFoundException;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.repository.UserRepository;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = this.userRepository.findByUsername(userDTO.getUsername());
        if (user != null) {
            throw new UserAlreadyExistException(userDTO.getUsername()+"Already exist");// TODO
        }
        User newUser=userDTO.convertToUser(userDTO);

        return this.userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long id, UserDTO userDTO) {
        this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        // TODO
        return this.userRepository.save(userDTO.convertToUser(userDTO));
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        this.userRepository.deleteById(id);
    }

    private void validateParams(UserDTO userDTO) {
        HashMap<String, String> errors = new HashMap<>();
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            errors.put("username", "Username cannot be empty");
        }
    }
}
