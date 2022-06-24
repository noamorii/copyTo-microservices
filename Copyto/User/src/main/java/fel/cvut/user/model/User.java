package fel.cvut.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Represents a User
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "System_user")
public class User extends AbstractEntity {

    private String firstName;
    private String surname;
    private String password;
    private String mobile;
    private String email;
    private Date dateOfBirth;
    private Role role;

    private String intro;

    /**
     * Encode the raw password.
     * @param passwordEncoder Service interface for encoding passwords
     */
    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }
}
