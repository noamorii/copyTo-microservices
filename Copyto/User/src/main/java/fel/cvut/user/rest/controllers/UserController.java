package fel.cvut.user.rest.controllers;

import fel.cvut.user.model.User;
import fel.cvut.user.rest.requests.UserRegistrationRequest;
import fel.cvut.user.security.application.ApplicationUser;
import fel.cvut.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/sendCookie")
    public void sendUser(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, IOException {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (o == "anonymousUser")
            throw new UserPrincipalNotFoundException("You're not logged in");

        ApplicationUser user = (ApplicationUser) o;
        response.sendRedirect("http://localhost:8081/api/v1/" + user.getId());
    }

    @PostMapping
    public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("new user registration {}", userRegistrationRequest);
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
