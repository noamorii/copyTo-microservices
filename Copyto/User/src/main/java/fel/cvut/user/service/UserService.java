package fel.cvut.user.service;

import fel.cvut.user.dao.UserDao;
import fel.cvut.user.model.Role;
import fel.cvut.user.model.User;
import fel.cvut.user.rest.requests.UserRegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder encoder;

    public void registerUser(UserRegistrationRequest request) {
        User user = User.builder()
                .firstName(request.firstName())
                .surname(request.surname())
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .password(request.password())
                .role(request.role())
                .mobile(request.mobile())
                .build();
        user.encodePassword(encoder);
        userDao.save(user);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }
}
