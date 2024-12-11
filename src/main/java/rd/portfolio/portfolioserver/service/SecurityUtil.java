package rd.portfolio.portfolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.exception.UserNotFoundException;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SecurityUtil {
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public static String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        return null;
    }

    public User ensureLoggedUser(Long requestId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authentication.getName());
        User user = this.userRepository.findById(requestId).orElseThrow(UserNotFoundException::new);
        if (!user.getUsername().equals(userDetails.getUsername())) {
            throw new UserNotFoundException();
        }

        return user;
    }
}
