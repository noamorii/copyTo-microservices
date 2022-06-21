package fel.cvut.user.rest.controllers;

import fel.cvut.user.model.User;
import fel.cvut.user.rest.requests.UserRegistrationRequest;
import fel.cvut.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("new user registration {}", userRegistrationRequest);
        log.warn(userRegistrationRequest.firstName());
        userService.registerUser(userRegistrationRequest);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(value = "current_user")
    public Object getCurrentUser() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
