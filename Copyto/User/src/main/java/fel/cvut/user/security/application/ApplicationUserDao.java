package fel.cvut.user.security.application;

import java.util.Optional;

public interface ApplicationUserDao {

    Optional<ApplicationUser> selectApplicationUserByEmail(String email);
}
