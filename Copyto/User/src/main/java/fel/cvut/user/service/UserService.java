package fel.cvut.user.service;

import fel.cvut.user.dao.UserDao;
import fel.cvut.user.model.Role;
import fel.cvut.user.model.User;
import fel.cvut.user.rest.requests.UserRegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserDao userDao;

    public void registerUser(UserRegistrationRequest request) {
        User user = User.builder()
                .firstName(request.firstName())
                .surname(request.surname())
                .email(request.email())
                .dateOfBirth(request.dateOfBirth())
                .password(request.password())
                .role(Role.USER)
                .mobile(request.mobile())
                .build();
        System.out.println(user.getFirstName());
        System.out.println(request.firstName());
        userDao.save(user);
    }
}
