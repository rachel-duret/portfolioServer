package rd.portfolio.portfolioserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.repository.UserRepository;

import java.util.Collections;

@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepository.findByUsername(username);
        if (appUser != null) {
            return new org.springframework.security.core.userdetails.User(appUser.getUsername(), appUser.getPassword(), Collections.singleton(new SimpleGrantedAuthority(appUser.getRole())));
        } else {
            throw new UsernameNotFoundException("Invalid name or password");
        }
    }

}
