package dataaccess;

import model.Game;
import websocket.commands.LeaveCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import java.util.Collection;

public interface GameDataAccess {

    Collection<Game> listGames();
    int newGame(String gameName);
    Game getGame(int gameID);
    void joinGame(String user, String color, int gameID);
    void clearApp();
    void updateGame(MakeMoveCommand command);
    void updateGame(LeaveCommand command);
}
