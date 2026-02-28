package service;

import dataaccess.GameDataAccess;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDataAccess;
import exceptions.DoesNotExistException;
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
        Assertions.assertEquals(userDAO.getUser("laurel"), registered);
    }

    @Test
    @DisplayName("Registration Negative")
    public void registrationFailure() {
        User registered = new User(null, "password!", "email@gmail.com");
        Assertions.assertThrows(DoesNotExistException.class,
                () -> userService.register(registered));
    }

}
