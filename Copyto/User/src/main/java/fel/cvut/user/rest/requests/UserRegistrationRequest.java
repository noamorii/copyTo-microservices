package fel.cvut.user.rest.requests;

import fel.cvut.user.model.Role;

import java.util.Date;

public record UserRegistrationRequest(
        String firstName,
        String surname,
        String password,
        String mobile,
        String email,
        Role role,
        Date dateOfBirth) {
}
