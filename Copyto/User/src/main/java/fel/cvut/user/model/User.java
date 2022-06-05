package fel.cvut.user.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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
}
