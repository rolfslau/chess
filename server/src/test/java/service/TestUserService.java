package service;

import dataaccess.GameDataAccess;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDataAccess;
import exceptions.DoesNotExistException;
import model.Auth;
import model.User;
import org.junit.jupiter.api.*;
import server.GameHandler;
import server.Server;
import chess.ChessGame;
import server.UserHandler;

public class TestUserService {

    public UserDataAccess userDAO;
    public GameDataAccess gameDAO;

    public UserService userService;
    public GameService gameService;

    @BeforeEach
    public void setup() {
        userDAO = new MemoryUserDAO();
        gameDAO = new MemoryGameDAO();

        userService = new UserService(userDAO);
        gameService = new GameService(gameDAO, userDAO);
    }

    @Test
    @DisplayName("Registration Positive")
    public void registrationSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Assertions.assertEquals(registered, userDAO.getUser("laurel"));
    }

    @Test
    @DisplayName("Registration Negative")
    public void registrationFailure() {
        User registered = new User(null, "password!", "email@gmail.com");
        Assertions.assertThrows(DoesNotExistException.class,
                () -> userService.register(registered));
    }

    @Test
    @DisplayName("Login Positive")
    public void loginSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        Assertions.assertEquals("laurel", userDAO.getAuth(auth.authToken()));
    }

    @Test
    @DisplayName("Login Negative")
    public void loginFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        User incorrect = new User("laurel", "blahblahblah", "email@gmail.com");
        Assertions.assertThrows(DoesNotExistException.class, () -> userService.login(incorrect));
    }

    @Test
    @DisplayName("Logout Positive")
    public void logoutSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        userService.logout(auth.authToken());
        Assertions.assertThrows(DoesNotExistException.class, () -> gameService.listGames(auth.authToken()));
    }

    @Test
    @DisplayName("Logout Negative")
    public void logoutFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        Assertions.assertThrows(DoesNotExistException.class, () -> userService.logout(auth.authToken() + "blahblahblah"));
    }

}
