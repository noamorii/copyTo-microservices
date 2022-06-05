package fel.cvut.user.rest;

import java.util.Date;

public record UserRegistrationRequest(
        String firstName,
        String surname,
        String password,
        String mobile,
        String email,
        Date dateOfBirth) {
}
