package rd.portfolio.portfolioserver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import rd.portfolio.portfolioserver.exception.UserNotFoundException;
import rd.portfolio.portfolioserver.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SecurityUtilTest {
    @Mock
    private UserDetailService userDetailService;
    @Mock
    private UserRepository userRepository;

    SecurityUtil securityUtil;

    @BeforeEach
    void setUp() {
        securityUtil = new SecurityUtil(userDetailService, userRepository);
    }


    @Test
    void ensureLoggedUser() {
        rd.portfolio.portfolioserver.model.User appUser = new rd.portfolio.portfolioserver.model.User(){{
            setId(1L);
            setUsername("test");
        }};

        Authentication authentication = new UsernamePasswordAuthenticationToken("test", "test");
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(userDetailService.loadUserByUsername("test")).thenReturn(new User("test", "",new ArrayList<>()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(appUser));
        var result = securityUtil.ensureLoggedUser(1L);
        assertNotNull(result);
        assertEquals("test", result.getUsername());
    }

    @Test
    void ensureLoggedUserNotFound() {
        rd.portfolio.portfolioserver.model.User appUser = new rd.portfolio.portfolioserver.model.User(){{
            setId(1L);
            setUsername("test");
        }};

        Authentication authentication = new UsernamePasswordAuthenticationToken("test1", "test");
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
        when(userDetailService.loadUserByUsername("test1")).thenReturn(new User("test1", "",new ArrayList<>()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(appUser));
        assertThrows(UserNotFoundException.class, () -> securityUtil.ensureLoggedUser(1L));
    }
}