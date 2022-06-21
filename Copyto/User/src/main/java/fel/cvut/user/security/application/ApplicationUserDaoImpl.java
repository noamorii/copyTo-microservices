package fel.cvut.user.security.application;

import fel.cvut.user.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ApplicationUserDaoImpl implements ApplicationUserDao {

    private final UserDao userDao;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByEmail(String email) {
        return Optional.of(
                new ApplicationUser(userDao.findByEmail(email))
        );
    }
}
