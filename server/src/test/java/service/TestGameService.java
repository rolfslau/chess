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
    @Order(3)
    @DisplayName("List Games Positive")
    public void listGamesSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        Collection<Game> games = gameService.listGames(auth.authToken());
        Assertions.assertTrue(games.isEmpty());
        gameService.newGame(auth.authToken(), "game name!!");
        Collection<Game> gamesNotEmpty = gameService.listGames(auth.authToken());
        Assertions.assertFalse(gamesNotEmpty.isEmpty());
    }

    @Test
    @Order(4)
    @DisplayName("List Games Negative")
    public void listGamesFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        Assertions.assertThrows(DoesNotExistException.class,
                () -> gameService.listGames(auth.authToken() + "blahblahblah"));
    }

    @Test
    @Order(1)
    @DisplayName("New Game Positive")
    public void newGameSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        int gameID = gameService.newGame(auth.authToken(), "game name!!");
        Assertions.assertEquals(new Game(1, null, null, "game name!!", new ChessGame()),
                gameDAO.getGame(gameID));
    }

    @Test
    @Order(2)
    @DisplayName("New Game Negative")
    public void newGameFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        Assertions.assertThrows(DoesNotExistException.class,
                () -> gameService.newGame(auth.authToken(), null));
    }


    @Test
    @Order(5)
    @DisplayName("Join Game Positive")
    public void joinGameSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        int gameID = gameService.newGame(auth.authToken(), "game name!!");
        gameService.joinGame(auth.authToken(), "WHITE", gameID);
        Assertions.assertEquals("laurel", gameDAO.getGame(gameID).whiteUsername());
    }


    @Test
    @Order(6)
    @DisplayName("Join Game Negative")
    public void joinGameFailure() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        gameService.newGame(auth.authToken(), "game name!!");
        Assertions.assertThrows(DoesNotExistException.class,
                () -> gameService.joinGame(auth.authToken(), "WHITE", 2));
    }


    @Test
    @Order(7)
    @DisplayName("Clear App Positive")
    public void clearAppSuccess() {
        User registered = new User("laurel", "password!", "email@gmail.com");
        userService.register(registered);
        Auth auth = userService.login(registered);
        gameService.newGame(auth.authToken(), "game name!!");
        gameService.clearApp();
        Assertions.assertNull(gameDAO.getGame(1));
        Assertions.assertNull(userDAO.getUser("laurel"));
        Assertions.assertNull(userDAO.getAuth(auth.authToken()));
    }

}
