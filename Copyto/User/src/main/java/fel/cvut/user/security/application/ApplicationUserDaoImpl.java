package fel.cvut.user.security.application;

import fel.cvut.user.dao.UserDao;
import fel.cvut.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
