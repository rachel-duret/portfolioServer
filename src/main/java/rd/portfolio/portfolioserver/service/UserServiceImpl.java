package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.dto.HobbyDTO;
import rd.portfolio.portfolioserver.dto.RoleType;
import rd.portfolio.portfolioserver.exception.HobbyNotFoundException;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
import rd.portfolio.portfolioserver.exception.UserAlreadyExistException;
import rd.portfolio.portfolioserver.exception.UserNotFoundException;
import rd.portfolio.portfolioserver.model.Hobby;
import rd.portfolio.portfolioserver.model.Profile;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.UpdateProfileParams;
import rd.portfolio.portfolioserver.params.UpdateUserParam;
import rd.portfolio.portfolioserver.params.UserParams;
import rd.portfolio.portfolioserver.repository.HobbyRepository;
import rd.portfolio.portfolioserver.repository.ProfileRepository;
import rd.portfolio.portfolioserver.repository.UserRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final HobbyRepository hobbyRepository;

    @Override
    public User getUserById(Long id) {

        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByLastnameAndFirstname(String lastname, String firstname) {
        return this.userRepository.findByLastnameAndFirstname(lastname, firstname).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User createUser(UserParams userParams) {
        this.validateParams(userParams);

        User newUser = new User();
        this.applyToUser(newUser, userParams);
        newUser.setHobbies(new ArrayList<>());

        User finalUser = this.userRepository.save(newUser);
        Profile profile = new Profile();
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
        return !this.userRepository.existsById(id);
    }

    @Override
    public Profile updateUserProfile(Long id, UpdateProfileParams updateProfileParams) {
        User user = this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        this.validateUpdateUserProfileParams(updateProfileParams);
        Profile profile = user.getProfile();
        this.applyToProfile(profile, updateProfileParams, user);
        if (!updateProfileParams.getHobbies().isEmpty()) {
            updateProfileParams.getHobbies().forEach(hobbyDTO -> {
                // TODO this update uncorrect
                this.updateOrCreateHobby(user, hobbyDTO);
            });
        }
        return this.profileRepository.save(profile);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    private void updateOrCreateHobby(User user, HobbyDTO hobbyDTO) {
        if (hobbyDTO.getId() == null) {
            Hobby newHobby = hobbyDTO.convertToHobby();
            newHobby.setUser(user);
            this.hobbyRepository.save(newHobby);
        } else {
            Hobby existHobby = this.hobbyRepository.findById(hobbyDTO.getId()).orElseThrow(HobbyNotFoundException::new);
            existHobby.setName(hobbyDTO.getName());
            existHobby.setImageUrl(hobbyDTO.getImageUrl());
            this.hobbyRepository.save(existHobby);
        }
    }

    private void validateParams(UserParams userParams) {
        HashMap<String, String> errors = new HashMap<>();
        if (userParams.getUsername() == null || userParams.getUsername().isEmpty()) {
            errors.put("username", "Username cannot be empty");
        }
        if (userParams.getFirstname() == null || userParams.getFirstname().isEmpty()) {
            errors.put("firstname", "firstname cannot be empty");
        }
        if (userParams.getLastname() == null || userParams.getLastname().isEmpty()) {
            errors.put("lastname", "lastname cannot be empty");
        }
        if (userParams.getPassword() == null || userParams.getPassword().isEmpty()) {
            errors.put("password", "password cannot be empty");
        }
        boolean userNameExist = this.isUserNameExist(userParams.getUsername());
        if (userNameExist) {
            errors.put("username", "Username already exist");
        }
        boolean isPresent = this.isUserLastnameAndFirstnameExist(userParams.getLastname(), userParams.getFirstname());
        if (isPresent) {
            errors.put("firstname", userParams.getLastname() + " already exist");
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
        if (!updateProfileParams.getHobbies().isEmpty()){
            updateProfileParams.getHobbies().forEach(hobbyDTO -> {
                if (hobbyDTO.getName() == null || hobbyDTO.getName().isEmpty()) {
                    errors.put("hobbyName", "Hobby name cannot be empty");
                }
            });
        }
        if (!errors.isEmpty()) {
            throw new InvalidParameterException();
        }
    }

    private void applyToUser(User user, UserParams userParams) {
        user.setUsername(userParams.getUsername());
        user.setFirstname(userParams.getFirstname());
        user.setLastname(userParams.getLastname());
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

    public boolean isUserNameExist(String name) {
        User user = this.userRepository.findByUsername(name).orElse(null);
        return user != null;
    }

    @Override
    public boolean isUserLastnameAndFirstnameExist(String lastname, String firstname) {
        return this.userRepository.findByLastnameAndFirstname(lastname, firstname).isPresent();
    }


}
