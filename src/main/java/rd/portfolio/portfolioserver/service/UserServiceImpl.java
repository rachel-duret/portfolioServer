package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.dto.RoleType;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
import rd.portfolio.portfolioserver.exception.UserNotFoundException;
import rd.portfolio.portfolioserver.model.Profile;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.UpdateProfileParams;
import rd.portfolio.portfolioserver.params.UpdateUserParam;
import rd.portfolio.portfolioserver.params.UserParams;
import rd.portfolio.portfolioserver.repository.ProfileRepository;
import rd.portfolio.portfolioserver.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

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

       User finalUser= this.userRepository.save(newUser);
        Profile profile= new Profile();
        profile.setAboutMe("I have nothing to say!");
        profile.setUser(finalUser);
        this.profileRepository.save(profile);
      return finalUser;
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

    @Override
    public Profile updateUserProfile(Long id, UpdateProfileParams updateProfileParams) {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        this.validateUpdateUserProfileParams(updateProfileParams);
        Profile profile = user.getProfile();
        this.applyToProfile(profile, updateProfileParams, user);
        return this.profileRepository.save(profile);
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

    private void validateUpdateUserProfileParams(UpdateProfileParams updateProfileParams) {
        HashMap<String, String> errors = new HashMap<>();
        if (updateProfileParams.getAboutMe() == null || updateProfileParams.getAboutMe().isEmpty()) {
            errors.put("aboutMe", "about me cannot be empty");
        }
        if (updateProfileParams.getImageUrl() == null || updateProfileParams.getImageUrl().isEmpty()) {
            errors.put("imageUrl", "Image url cannot be empty");
        }
        if (!errors.isEmpty()) {
            throw new InvalidParameterException();
        }
    }

    private void applyToUser(User user, UserParams userParams) {
        user.setUsername(userParams.getUsername());
        user.setPassword(passwordEncoder.encode(userParams.getPassword()));
        user.setEmail(userParams.getEmail());

        if (Objects.equals(userParams.getUsername(), "rachel")) {
            user.setRole(RoleType.ADMIN.name());
        } else {
            user.setRole(RoleType.USER.name());
        }
    }

    private void applyToUser(User user, UpdateUserParam updateUserParam) {
        user.setUsername(updateUserParam.getUsername());
        user.setEmail(updateUserParam.getEmail());
        user.setUpdatedAt(Timestamp.from(Instant.now()));
        if (Objects.equals(updateUserParam.getUsername(), "rachel")) { // TODO move this to config
            user.setRole(RoleType.ADMIN.name());
        } else {
            user.setRole(RoleType.USER.name());
        }
    }

    private void applyToProfile(Profile profile, UpdateProfileParams updateProfileParams, User user) {
        profile.setBirthday(LocalDate.parse(updateProfileParams.getBirthday()));
        profile.setPhone(updateProfileParams.getPhoneNumber());
        profile.setImageUrl(updateProfileParams.getImageUrl());
        profile.setAboutMe(updateProfileParams.getAboutMe());
        profile.setProfession(updateProfileParams.getProfession());
        profile.setSex(updateProfileParams.getSex());
        profile.setUpdatedAt(Timestamp.from(Instant.now()));
        profile.setUser(user);
    }

    public boolean isUserExist(String name) {
        User user = this.userRepository.findByUsername(name);
        return user != null;
    }


}
