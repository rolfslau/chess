package dataaccess;

import exceptions.DataBaseException;
import model.Auth;
import model.User;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import service.GameService;
import service.UserService;

import java.util.UUID;

public class UserDAOTest {

    public final UserDataAccess userDAO = new MySqlUserDAO();
    public final GameDataAccess gameDAO = new MySqlGameDAO();


    @BeforeEach
    public void setup() {
        gameDAO.clearApp();
        userDAO.clearApp();
    }

    @Test
    @DisplayName("Register Positive")
    public void registrationSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userDAO.register(registered);
        Assertions.assertTrue(BCrypt.checkpw("password!", userDAO.getUser("laurel").password()));
    }

    @Test
    @DisplayName("Register Negative")
    public void registrationFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userDAO.register(registered);
        Assertions.assertThrows(DataBaseException.class, () -> userDAO.register(registered));
    }

    @Test
    @DisplayName("Login Positive")
    public void loginSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userDAO.register(registered);
        String authToken = UUID.randomUUID().toString();
        Auth auth = new Auth(authToken, "laurel");
        userDAO.authorization(auth);
        Assertions.assertEquals("laurel", userDAO.getAuth(authToken));
    }

    @Test
    @DisplayName("Login Negative")
    public void loginFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userDAO.register(registered);
        String authToken = UUID.randomUUID().toString();
        Auth auth = new Auth(authToken, "laurel");
        userDAO.authorization(auth);
        Assertions.assertNull(userDAO.getUser("notLaurel"));
    }

    @Test
    @DisplayName("Logout Positive")
    public void logoutSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userDAO.register(registered);
        String authToken = UUID.randomUUID().toString();
        Auth auth = new Auth(authToken, "laurel");
        userDAO.authorization(auth);
        userDAO.logout(authToken);
        Assertions.assertNull(userDAO.getAuth(authToken));
    }

    @Test
    @DisplayName("Logout Negative")
    public void logoutFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userDAO.register(registered);
        String authToken = UUID.randomUUID().toString();
        Auth auth = new Auth(authToken, "laurel");
        userDAO.authorization(auth);
        userDAO.logout("notAuthToken" + authToken);
        Assertions.assertEquals("laurel", userDAO.getAuth(authToken));
    }

    @Test
    @DisplayName("Clear App User")
    public void clearAppUser() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userDAO.register(registered);
        String authToken = UUID.randomUUID().toString();
        Auth auth = new Auth(authToken, "laurel");
        userDAO.authorization(auth);
        userDAO.clearApp();
        Assertions.assertNull(userDAO.getAuth(authToken));
        Assertions.assertNull(userDAO.getUser("laurel"));
    }


}
