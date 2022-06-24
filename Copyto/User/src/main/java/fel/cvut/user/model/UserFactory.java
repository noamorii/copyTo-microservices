package fel.cvut.user.model;

import java.util.Date;

/**
 * Design pattern Factory, which allows you to create users with different roles
 */
public class UserFactory {

    /**
     * Creates admin user
     * @return Admin User
     */
    public User createAdmin(String firstname, String surname, String password, String mobile,
                            String email, Date dateOfBirth, String intro) {
        return new User(firstname, surname, password, mobile, email, dateOfBirth, Role.ADMIN, intro);
    }

    /**
     * Creates client user
     * @return Client User
     */
    public User createClient(String firstname, String surname, String password, String mobile,
                            String email, Date dateOfBirth, String intro) {
        return new User(firstname, surname, password, mobile, email, dateOfBirth, Role.CLIENT, intro);
    }

    /**
     * Creates copywriter user
     * @return Copywriter User
     */
    public User createCopywriter(String firstname, String surname, String password, String mobile,
                            String email, Date dateOfBirth, String intro) {
        return new User(firstname, surname, password, mobile, email, dateOfBirth, Role.COPYWRITER, intro);
    }
}
