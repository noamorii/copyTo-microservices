package fel.cvut.user.rest.requests;

import java.util.Date;

public record UserRegistrationRequest(
        String firstName,
        String surname,
        String password,
        String mobile,
        String email,
        Date dateOfBirth) {
}
