package service;

import dataaccess.GameDataAccess;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDataAccess;
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

}
