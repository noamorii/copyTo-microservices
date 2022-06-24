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

/**
 * Controller class is responsible for processing incoming REST API requests, preparing a model, and returning the view to be rendered as a response.
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * Sends a request for to the Order microservice with the card user if he is logged in, if he is not logged in he will throw an exception
     * @param response server response
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */
    @GetMapping(value = "/sendCookie")
    public void sendUser(HttpServletResponse response) throws IOException {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (o == "anonymousUser")
            throw new UserPrincipalNotFoundException("You're not logged in");

        ApplicationUser user = (ApplicationUser) o;
        response.sendRedirect("http://localhost:8081/api/v1/" + user.getId());
    }

    /**
     *  Creates a new user
     * @param userRegistrationRequest request with data to create a new user
     */
    @PostMapping
    public void registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        log.info("new user registration {}", userRegistrationRequest);
        userService.registerUser(userRegistrationRequest);
    }

    /**
     * Returns all users
     * @return List of users
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Returns the current user
     * @return Object represents the current user
     */
    @GetMapping(value = "current_user")
    public Object getCurrentUser() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
