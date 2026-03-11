package dataaccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameDAOTest {

    public final UserDataAccess userDAO = new MySqlUserDAO();
    public final GameDataAccess gameDAO = new MySqlGameDAO();

    @BeforeEach
    public void setup() {
        gameDAO.clearApp();
        userDAO.clearApp();
    }

    @Test
    @DisplayName("Create Game Positive")
    public void createGameSuccess() {
        int id = gameDAO.newGame("newGame!!");
        Assertions.assertEquals(1, id);
    }

    @Test
    @DisplayName("Create Game Negative")
    public void createGameFailure() {
        int id = gameDAO.newGame("newGame!!");
        Assertions.assertNull(gameDAO.getGame(id + 1));
    }

    @Test
    @DisplayName("List Games Positive")
    public void listGamesSuccess() {
        gameDAO.newGame("newGame!!");
        gameDAO.newGame("newGame2");
        Assertions.assertEquals(2, gameDAO.listGames().size());
    }

    @Test
    @DisplayName("List Games Negative")
    public void listGamesFailure() {
        gameDAO.newGame("newGame!!");
        gameDAO.newGame("newGame2");
        Assertions.assertNotEquals(3, gameDAO.listGames().size());
    }

    @Test
    @DisplayName("Join Game Positive")
    public void joinGameSuccess() {
        int id = gameDAO.newGame("newGame!!");
        gameDAO.joinGame("laurel :)", "WHITE", id);
        Assertions.assertEquals("laurel :)", gameDAO.getGame(id).whiteUsername());
    }

    @Test
    @DisplayName("Join Game Negative")
    public void joinGameFailure() {
        int id = gameDAO.newGame("newGame!!");
        gameDAO.joinGame("laurel :)", "WHITE", id+1);
        Assertions.assertNull(gameDAO.getGame(id).whiteUsername());
    }

    @Test
    @DisplayName("Clear App Game")
    public void clearAppGame() {
        int id = gameDAO.newGame("newGame!!");
        gameDAO.clearApp();
        Assertions.assertNull(gameDAO.getGame(id));
        Assertions.assertEquals(0, gameDAO.listGames().size());
    }
}
