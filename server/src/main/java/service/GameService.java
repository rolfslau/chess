package service;

import Things.Game;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import exceptions.DoesNotExistException;

import java.util.Collection;

public class GameService {

    public final MemoryGameDAO dataAccess;
    public final MemoryUserDAO uData;

    public GameService(MemoryGameDAO dataAccess, MemoryUserDAO uData) {
        this.dataAccess = dataAccess;
        this.uData = uData;
    }

    public Collection<Game> listGames(String auth) throws DoesNotExistException {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("not authorized");
        }
        return dataAccess.listGames();
    }

    public int newGame(String auth, String gameName) throws DoesNotExistException {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("not authorized");
        }
        return dataAccess.newGame(gameName, uData.getAuth(auth));
    }



}
