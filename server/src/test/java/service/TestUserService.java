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

    UserDataAccess userDAO = new MemoryUserDAO();
    GameDataAccess gameDAO = new MemoryGameDAO();

    UserService userService = new UserService(userDAO);
    GameService gameService = new GameService(gameDAO, userDAO);

    @BeforeEach
    public void setup() {

    }

}
