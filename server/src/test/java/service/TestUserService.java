package service;

import dataaccess.*;
import exceptions.DoesNotExistException;
import model.Auth;
import model.User;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;

public class TestUserService {

    public UserDataAccess userDAO;
    public GameDataAccess gameDAO;

    public UserService userService;
    public GameService gameService;

    @BeforeEach
    public void setup() {
        userDAO = new MySqlUserDAO();
        gameDAO = new MySqlGameDAO();

        userService = new UserService(userDAO);
        gameService = new GameService(gameDAO, userDAO);

        gameService.clearApp();
    }

    @Test
    @Order(1)
    @DisplayName("Registration Positive")
    public void registrationSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Assertions.assertTrue(BCrypt.checkpw("password!", userDAO.getUser("laurel").password()));
    }

    @Test
    @Order(2)
    @DisplayName("Registration Negative")
    public void registrationFailure() {
        User registered = new User(null, "password!", "email@gmail.com");
        Assertions.assertThrows(DoesNotExistException.class,
                () -> userService.register(registered));
    }

    @Test
    @Order(3)
    @DisplayName("Login Positive")
    public void loginSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        Assertions.assertEquals("laurel", userDAO.getAuth(auth.authToken()));
    }

    @Test
    @Order(4)
    @DisplayName("Login Negative")
    public void loginFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        User incorrect = new User("laurel", "blahblahblah", "email@gmail.com");
        Assertions.assertThrows(DoesNotExistException.class, () -> userService.login(incorrect));
    }

    @Test
    @Order(5)
    @DisplayName("Logout Positive")
    public void logoutSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        userService.logout(auth.authToken());
        Assertions.assertThrows(DoesNotExistException.class, () -> gameService.listGames(auth.authToken()));
    }

    @Test
    @Order(6)
    @DisplayName("Logout Negative")
    public void logoutFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        Assertions.assertThrows(DoesNotExistException.class, () -> userService.logout(auth.authToken() + "blahblahblah"));
    }

}
