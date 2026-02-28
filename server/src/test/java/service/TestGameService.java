package service;

import dataaccess.GameDataAccess;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDataAccess;
import org.junit.jupiter.api.*;
import server.Server;
import chess.ChessGame;

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

    }


}
