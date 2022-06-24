package fel.cvut.user.rest.requests;

import fel.cvut.user.model.Role;

import java.util.Date;

/**
 * Request which has all user parameters
 */
public record UserRegistrationRequest(
        String firstName,
        String surname,
        String password,
        String mobile,
        String email,
        Date dateOfBirth,
        Role role) {
}
