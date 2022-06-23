package fel.cvut.user.model;

import java.util.Date;

public class UserFactory {

    public User createAdmin(String firstname, String surname, String password, String mobile,
                            String email, Date dateOfBirth, String intro) {
        return new User(firstname, surname, password, mobile, email, dateOfBirth, Role.ADMIN, intro);
    }

    public User createClient(String firstname, String surname, String password, String mobile,
                            String email, Date dateOfBirth, String intro) {
        return new User(firstname, surname, password, mobile, email, dateOfBirth, Role.CLIENT, intro);
    }

    public User createCopywriter(String firstname, String surname, String password, String mobile,
                            String email, Date dateOfBirth, String intro) {
        return new User(firstname, surname, password, mobile, email, dateOfBirth, Role.COPYWRITER, intro);
    }
}
