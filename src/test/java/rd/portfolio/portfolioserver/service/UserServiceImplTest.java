package rd.portfolio.portfolioserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import rd.portfolio.portfolioserver.dto.HobbyDTO;
import rd.portfolio.portfolioserver.exception.InvalidParameterException;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private HobbyRepository hobbyRepository;
    private UserServiceImpl userService;
    private User user;
    private UserParams userParams;

    public static Stream<Arguments> validateUserParamsProvider() {
        return Stream.of(
                Arguments.of(null, "firstname", "lastname", "password", "user@example.com"),
                Arguments.of("", "firstname", "lastname", "password", "user@example.com"),
                Arguments.of("username", null, "lastname", "password", "user@example.com"),
                Arguments.of("username", "", "lastname", "password", "user@example.com"),
                Arguments.of("username", "firstname", null, "password", "user@example.com"),
                Arguments.of("username", "firstname", "", "password", "user@example.com"),
                Arguments.of("username", "firstname", "lastname", null, "user@example.com"),
                Arguments.of("username", "firstname", "lastname", "", "user@example.com")

        );
    }

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder, profileRepository, hobbyRepository);
        user = new User(){{
            setId(1L);
            setUsername("username");
            setFirstname("firstname");
            setLastname("lastname");
            setPassword("password");
            setEmail("user@example.com");
            setRole("USER");
            setProfile(new Profile());
            setHobbies(new ArrayList<>());
        }};
        userParams = new UserParams(){{
            setUsername("username");
            setFirstname("firstname");
            setLastname("lastname");
            setPassword("password");
            setEmail("user@example.com");
        }};
    }

    @Test
    void getUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        var result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("username", result.getUsername());
    }

    @Test
    void getUserByIdThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserByLastnameAndFirstname() {
        when(userRepository.findByLastnameAndFirstname("lastname", "firstname")).thenReturn(Optional.of(user));
        var result = userService.getUserByLastnameAndFirstname("lastname", "firstname");
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("username", result.getUsername());
        assertEquals("firstname", result.getFirstname());
    }

    @Test
    void getUserByLastnameAndFirstnameThrowException() {
        when(userRepository.findByLastnameAndFirstname("lastname", "firstname")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByLastnameAndFirstname("lastname", "firstname"));
    }

    @ParameterizedTest
    @MethodSource("validateUserParamsProvider")
    void validateUserParams(String username, String firstname, String lastname, String password, String email) {
        userParams.setUsername(username);
        userParams.setFirstname(firstname);
        userParams.setLastname(lastname);
        userParams.setPassword(password);
        userParams.setEmail(email);
        when(userRepository.findByLastnameAndFirstname(lastname, firstname)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        assertThrows(InvalidParameterException.class, ()->userService.createUser(userParams));
    }

    @Test
    void createUserRoleAdmin() {
        userParams.setUsername("rachel");
        user.setRole("ADMIN");
        when(userRepository.save(any())).thenReturn(user);
        var result = userService.createUser(userParams);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ADMIN", result.getRole());

    }

    @Test
    void createUserRoleUser() {
        when(userRepository.save(any())).thenReturn(user);
        var result = userService.createUser(userParams);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("USER", result.getRole());

    }

    @Test
    void updateUser() {
        UpdateUserParam updateUserParam = new UpdateUserParam(){{
            setUsername("username");
        }};
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        user.setUsername("username");
        when(userRepository.save(any())).thenReturn(user);
        var result = userService.updateUser(1L, updateUserParam);
        assertNotNull(result);
        assertEquals("username", result.getUsername());
    }

    @Test
    void updateUserNotExistThrowException() {
        UpdateUserParam updateUserParam = new UpdateUserParam(){{
            setUsername("username");
        }};
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, ()->userService.updateUser(1L,updateUserParam ));
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUserThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, ()->userService.deleteUser(1L));

        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void isUserExist() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertTrue(userService.isUserExist(1L));
    }

    @Test
    void updateUserProfileWithNewHobby() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        HobbyDTO hobbyDTO = new HobbyDTO(){{
            setName("hobby");
            setImageUrl("https://www.hobby.com");
        }};
        Hobby hobby = new Hobby(){{
            setId(1L);
            setName("hobby");
            setImageUrl("https://www.hobby.com");
        }};
       user.getHobbies().add(hobby);
        UpdateProfileParams profileParams = new UpdateProfileParams(){{
            setBirthday("2025-03-13");
            setPhoneNumber("123456789");
            setImageUrl("https://image.com");
            setAboutMe("this is a test");
            setProfession("profession");
            setSex("f");
            setHobbies(new ArrayList<>(){{
                add(hobbyDTO);
            }});
        }};
        Profile profile = new Profile(){{
            setId(1L);
            setBirthday(LocalDate.parse("2025-03-13"));
            setPhone("123456789");
            setImageUrl("https://image.com");
            setAboutMe("this is a test");
            setProfession("profession");
            setSex("f");
        }};
        when(hobbyRepository.save(any())).thenReturn(hobby);
        when(profileRepository.save(any())).thenReturn(profile);
        var result = userService.updateUserProfile(1L, profileParams);
        assertEquals("123456789", result.getPhone());
        assertEquals("this is a test", result.getAboutMe());
        assertEquals(1, user.getHobbies().size());
        verify(hobbyRepository, never()).findById(any());

    }

    @Test
    void updateUserProfile() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        HobbyDTO hobbyDTO = new HobbyDTO(){{
            setId(1L);
            setName("hobby updated");
            setImageUrl("https://www.hobby.com");
        }};
        Hobby hobby = new Hobby(){{
            setId(1L);
            setName("hobby");
            setImageUrl("https://www.hobby.com");
        }};
        user.getHobbies().add(hobby);
        UpdateProfileParams profileParams = new UpdateProfileParams(){{
            setBirthday("2025-03-13");
            setPhoneNumber("123456789");
            setImageUrl("https://image.com");
            setAboutMe("this is a test");
            setProfession("profession");
            setSex("f");
            setHobbies(new ArrayList<>(){{
                add(hobbyDTO);
            }});
        }};
        Profile profile = new Profile(){{
            setId(1L);
            setBirthday(LocalDate.parse("2025-03-13"));
            setPhone("123456789");
            setImageUrl("https://image.com");
            setAboutMe("this is a test");
            setProfession("profession");
            setSex("f");
        }};
      when(hobbyRepository.findById(1L)).thenReturn(Optional.of(hobby));
        when(profileRepository.save(any())).thenReturn(profile);
        when(hobbyRepository.save(any())).thenReturn(hobby);

        var result = userService.updateUserProfile(1L, profileParams);
        assertEquals("123456789", result.getPhone());
        assertEquals("this is a test", result.getAboutMe());
        assertEquals(1, user.getHobbies().size());
        assertEquals("hobby updated", user.getHobbies().get(0).getName());

    }

    @Test
    void getUserByUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        var result = userService.getUserByUsername("username");
        assertEquals("username", result.getUsername());
    }

    @Test
    void getUserByUsernameThrowException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("username"));
    }

    @Test
    void isUserNameExist() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        assertTrue(userService.isUserNameExist("username"));
    }

    @Test
    void isUserLastnameAndFirstnameExist() {
        when(userRepository.findByLastnameAndFirstname("test", "li")).thenReturn(Optional.empty());
        assertFalse(userService.isUserLastnameAndFirstnameExist("test", "li"));
    }
}