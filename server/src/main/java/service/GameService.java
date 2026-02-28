package service;

import dataaccess.GameDataAccess;
import dataaccess.UserDataAccess;
import model.Game;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import exceptions.AlreadyExistsException;
import exceptions.DoesNotExistException;

import java.util.Collection;
import java.util.Objects;

public class GameService {

    public final GameDataAccess dataAccess;
    public final UserDataAccess uData;

    public GameService(GameDataAccess dataAccess, UserDataAccess uData) {
        this.dataAccess = dataAccess;
        this.uData = uData;
    }

    public Collection<Game> listGames(String auth) throws DoesNotExistException {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        return dataAccess.listGames();
    }

    public int newGame(String auth, String gameName) throws DoesNotExistException {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        if (gameName == null) {
            throw new DoesNotExistException("Error: invalid game name", 400);
        }
        return dataAccess.newGame(gameName);
    }

    public void joinGame(String auth, String color, int gameID) throws DoesNotExistException, AlreadyExistsException {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        String user = uData.getAuth(auth);
        Game game = dataAccess.getGame(gameID);
        if (game == null) {
            throw new DoesNotExistException("Error: no game by that id", 400);
        }
        if (!Objects.equals(color, "WHITE") && !Objects.equals(color, "BLACK")) {
            throw new DoesNotExistException("Error: not a valid color", 400);
        }
        if (colorTaken(game, color)) {
            throw new AlreadyExistsException("Error: color already taken", 403);
        }
        dataAccess.joinGame(user, color, gameID);
    }

    private Boolean colorTaken(Game game, String color) {
        if (!Objects.equals(game.whiteUsername(), null) && Objects.equals(color, "WHITE")) {
            return true;
        }
        else { return !Objects.equals(game.blackUsername(), null) && Objects.equals(color, "BLACK"); }
    }

    public void clearApp() {
        uData.clearApp();
        dataAccess.clearApp();
    }

}
