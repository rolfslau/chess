package service;

import dataaccess.GameDataAccess;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDataAccess;
import exceptions.DoesNotExistException;
import model.Auth;
import model.Game;
import model.User;
import org.junit.jupiter.api.*;
import server.Server;
import chess.ChessGame;

import java.util.Collection;

public class TestGameService {

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
    @Order(1)
    @DisplayName("List Games Positive")
    public void listGamesSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        gameService.newGame(auth.authToken(), "game name!!");
        Collection<Game> games = gameService.listGames(auth.authToken());
        Assertions.assertFalse(games.isEmpty());
    }

    @Test
    @Order(1)
    @DisplayName("List Games Negative")
    public void listGamesFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        Assertions.assertThrows(DoesNotExistException.class,
                () -> gameService.listGames(auth.authToken() + "blahblahblah"));
    }


}
