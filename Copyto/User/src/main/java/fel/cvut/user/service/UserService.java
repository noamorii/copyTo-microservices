package fel.cvut.user.service;

import fel.cvut.user.dao.UserDao;
import fel.cvut.user.model.User;
import fel.cvut.user.model.UserFactory;
import fel.cvut.user.rest.requests.UserRegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder encoder;

    public void registerUser(UserRegistrationRequest request) {
        UserFactory userFactory = new UserFactory();
        User user = null;
        switch (request.role()) {
            case ADMIN -> user = userFactory.createAdmin(request.firstName(), request.surname(), request.password(),
                    request.mobile(), request.email(), request.dateOfBirth(), "");
            case CLIENT -> user = userFactory.createClient(request.firstName(), request.surname(), request.password(),
                    request.mobile(), request.email(), request.dateOfBirth(), "");
            case COPYWRITER ->
                    user = userFactory.createCopywriter(request.firstName(), request.surname(), request.password(),
                            request.mobile(), request.email(), request.dateOfBirth(), "");
        }
        user.encodePassword(encoder);
        userDao.save(user);
    }

    @CachePut(value = "users", key = "#user")
    public void update(User user) {
        Objects.requireNonNull(user);
        userDao.save(user);
    }

    @CacheEvict(value = "orders", key = "#user")
    public void delete(User user) {
        Objects.requireNonNull(user);
        userDao.delete(user);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Cacheable(value = "orders", key = "#id")
    public User findById(Integer id) {
        return userDao.findById(id).orElse(null);
    }
}
