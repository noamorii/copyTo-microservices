package fel.cvut.user.service;

import fel.cvut.user.dao.UserDao;
import fel.cvut.user.model.Role;
import fel.cvut.user.model.User;
import fel.cvut.user.rest.requests.UserRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserDao userDao;
    @Mock
    PasswordEncoder encoder;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @InjectMocks
    UserService userService;

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testRegisterUser() {
        //Arrange
        String expectedName = "Jiri";
        String expectedSurname = "Sebek";
        String expectedPassword = "admin";
        String expectedMobile = "777888999";
        String expectedEmail = "sebekjir@ntk.cz";
        Calendar calendar = Calendar.getInstance();
        calendar.set(1998, Calendar.JANUARY, 1, 0, 0, 0);
        Date expectedBirthDay = calendar.getTime();
        Role expectedRole = Role.ADMIN;
        UserRegistrationRequest request = new UserRegistrationRequest(expectedName, expectedSurname, expectedPassword,
                expectedMobile, expectedEmail, expectedBirthDay, expectedRole);

        //Act
        userService.registerUser(request);

        //Assert
        Mockito.verify(userDao, Mockito.times(1)).save(userArgumentCaptor.capture());
        User user = userArgumentCaptor.getValue();
        assertAll(
                ()->assertEquals(expectedName, user.getFirstName()),
                ()->assertEquals(expectedSurname, user.getSurname()),
                ()->assertEquals(expectedMobile, user.getMobile()),
                ()->assertEquals(expectedEmail, user.getEmail()),
                ()->assertEquals(expectedBirthDay, user.getDateOfBirth()),
                ()->assertEquals(expectedRole, user.getRole()));
    }

    @Test
    void testUpdate() {
        //Act
        userService.update(user);

        //Assert
        Mockito.verify(userDao, Mockito.times(1)).save(user);
    }

    @Test
    void testDelete() {
        //Act
        userService.delete(user);

        //Assert
        Mockito.verify(userDao, Mockito.times(1)).delete(user);
    }

    @Test
    void testFindAllUsers() {
        //Act
        userService.findAllUsers();

        //Assert
        Mockito.verify(userDao, Mockito.times(1)).findAll();

    }

    @Test
    void testFindById() {
        //Arrange
        int expectedId = 1;

        //Act
        userService.findById(expectedId);

        //Assert
        Mockito.verify(userDao, Mockito.times(1)).findById(expectedId);
    }
}