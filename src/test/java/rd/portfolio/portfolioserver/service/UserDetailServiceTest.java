package rd.portfolio.portfolioserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rd.portfolio.portfolioserver.exception.UserNotFoundException;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceTest {
    @Mock
    private UserRepository userRepository;
    private UserDetailService userDetailService;
    private User user;

    @BeforeEach
    void setUp() {
        userDetailService = new UserDetailService(userRepository);
        user = new User(){{
            setId(1L);
            setUsername("test");
            setPassword("test_password");
            setRole("ROLE_USER");
        }};
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        var result = userDetailService.loadUserByUsername("test");
        assertNotNull(result);
        assertEquals("test", result.getUsername());
    }

    @Test
    void loadUserByUsernameUserNotExist() {
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailService.loadUserByUsername("test"));
    }
}