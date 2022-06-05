package fel.cvut.user.rest;

import fel.cvut.user.UserDao;
import fel.cvut.user.model.Role;
import fel.cvut.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDao userDao;

    public void registerUser(UserRegistrationRequest req) {
        User user = User.builder()
                .firstName(req.firstName())
                .surname(req.surname())
                .email(req.email())
                .dateOfBirth(req.dateOfBirth())
                .password(req.password())
                .role(Role.USER)
                .mobile(req.mobile())
                .build();
        System.out.println(user.getFirstName());
        System.out.println(req.firstName());
        userDao.save(user);
    }
}
