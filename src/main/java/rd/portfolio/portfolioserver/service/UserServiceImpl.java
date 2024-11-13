package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.dto.RoleType;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
import rd.portfolio.portfolioserver.exception.UserNotFoundException;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.UpdateUserParam;
import rd.portfolio.portfolioserver.params.UserParams;
import rd.portfolio.portfolioserver.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id) {

        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByName(String name) {
        return this.userRepository.findByUsername(name);
    }

    @Override
    public User createUser(UserParams userParams) {
        User user = this.userRepository.findByUsername(userParams.getUsername());
        this.validateParams(user, userParams);
        User newUser = new User();
        this.applyToUser(newUser, userParams);

        return this.userRepository.save(newUser);
    }

    @Override
    public User updateUser(Long id, UpdateUserParam updateUserParam) {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        this.validateUpdateUserParams(updateUserParam);
        this.applyToUser(user, updateUserParam);
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        this.userRepository.deleteById(id);
    }

    @Override
    public boolean isUserExist(Long id) {
        return this.userRepository.existsById(id);
    }

    private void validateParams(User user, UserParams userParams) {
        HashMap<String, String> errors = new HashMap<>();
        if (user != null) {
            errors.put("username", user.getUsername() + " already exist");
        }
        if (userParams.getUsername() == null || userParams.getUsername().isEmpty()) {
            errors.put("username", "Username cannot be empty");
        }
        if (!errors.isEmpty()) {
            // TODO put error message to exception
            throw new InvalidParameterException();
        }
    }

    private void validateUpdateUserParams(UpdateUserParam updateUserParam) {
        HashMap<String, String> errors = new HashMap<>();
        if (updateUserParam.getUsername() == null || updateUserParam.getUsername().isEmpty()) {
            errors.put("username", "Username cannot be empty");
        }
        if (!errors.isEmpty()) {
            // TODO put error message to exception
            throw new InvalidParameterException();
        }
    }

    private void applyToUser(User user, UserParams userParams) {
        user.setUsername(userParams.getUsername());
        user.setPassword(passwordEncoder.encode(userParams.getPassword()));
        user.setEmail(userParams.getEmail());
        user.setSex(userParams.getSex());
        user.setProfession(userParams.getProfession());
        if (Objects.equals(userParams.getUsername(), "rachel")) {
            user.setRole(RoleType.ADMIN.name());
        } else {
            user.setRole(RoleType.USER.name());
        }
    }

    private void applyToUser(User user, UpdateUserParam updateUserParam) {
        user.setUsername(updateUserParam.getUsername());
        user.setEmail(updateUserParam.getEmail());
        user.setSex(updateUserParam.getSex());
        user.setAboutMe(updateUserParam.getAboutMe());
        user.setProfession(updateUserParam.getProfession());
        user.setBirthday(updateUserParam.getBirthday());
        user.setProfession(updateUserParam.getProfession());
        user.setPhone(updateUserParam.getPhoneNumber());
        user.setImageUrl(updateUserParam.getImageUrl());
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        if (Objects.equals(updateUserParam.getUsername(), "rachel")) {
            user.setRole(RoleType.ADMIN.name());
        } else {
            user.setRole(RoleType.USER.name());
        }
    }

    public boolean isUserExist(String name) {
        User user = this.userRepository.findByUsername(name);
        return user != null;
    }
}
