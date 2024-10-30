package rd.portfolio.portfolioserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rd.portfolio.portfolioserver.dto.UserDTO;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user =this.userService.getUserById(id);
        return user.conventToDTO(user);
    }

    @PostMapping()
    public UserDTO createUser(Authentication authentication, @RequestBody UserDTO userDTO) {
        User user =this.userService.createUser(userDTO);
        return user.conventToDTO(user);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(Authentication authentication,@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user =this.userService.updateUser(id, userDTO);
        return user.conventToDTO(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(Authentication authentication,@PathVariable Long id) {
        this.userService.deleteUser(id);
    }

}
