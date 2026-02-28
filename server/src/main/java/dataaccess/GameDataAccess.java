package dataaccess;

import model.Game;

import java.util.Collection;

public interface GameDataAccess {

    Collection<Game> listGames();
    int newGame(String gameName);
    Game getGame(int gameID);
    void joinGame(String user, String color, int gameID);
    void clearApp();
}
