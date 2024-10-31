package rd.portfolio.portfolioserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rd.portfolio.portfolioserver.dto.UserDTO;
import rd.portfolio.portfolioserver.exception.UserNotFoundException;
import rd.portfolio.portfolioserver.model.User;
import rd.portfolio.portfolioserver.params.UserParams;
import rd.portfolio.portfolioserver.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        try {
            User user = this.userService.getUserById(id);
            return new ResponseEntity<>(user.conventToDTO(), HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserParams userParam) {
        boolean exist = this.userService.isUserExist(userParam.getUsername());
        if (exist) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserDTO userDTO = this.userService.createUser(userParam).conventToDTO();
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public UserDTO updateUser(Authentication authentication, @PathVariable Long id, @RequestBody UserParams userParams) {
        if (!this.userService.isUserExist(authentication.getName())) {
            throw new UserNotFoundException(authentication.getName());
        }
        User user = this.userService.updateUser(id, userParams);
        return user.conventToDTO();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(Authentication authentication, @PathVariable Long id) {
        this.userService.deleteUser(id);
    }

}
