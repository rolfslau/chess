package dataaccess;

import model.Game;
import chess.ChessGame;
import websocket.commands.GameOnCommand;
import websocket.commands.LeaveCommand;
import websocket.commands.MakeMoveCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class MemoryGameDAO implements GameDataAccess {

    int ids = 1;
    final public HashMap<Integer, Game> games = new HashMap<>();

    public MemoryGameDAO() {}

    public Collection<Game> listGames() {
        if (games.isEmpty()) {
            return new ArrayList<>();
        }
        return games.values();
    }

    public int newGame(String gameName) {
        Game game = new Game(ids, null, null, gameName, true, new ChessGame());
        games.put(ids, game);
        return ids++;
    }

    public Game getGame(int gameID) {
        return games.get(gameID);
    }

    public void joinGame(String user, String color, int gameID) {
        System.out.println("made it to joingame in MEMORY DAO :((");
        Game game = games.get(gameID);
        if (Objects.equals(color, "WHITE")) {
            games.remove(gameID);
            game = new Game(game.gameID(), user, game.blackUsername(), game.gameName(), true, game.game());
            games.put(gameID, game);
        }
        else {
            games.remove(gameID);
            game = new Game(game.gameID(), game.whiteUsername(), user, game.gameName(), true, game.game());
            games.put(gameID, game);
        }
    }

    @Override
    public void updateGame(LeaveCommand command) {

    }

    @Override
    public void updateGame(GameOnCommand command) {

    }

    @Override
    public void updateGame(MakeMoveCommand command) {

    }

    public void clearApp() {
        games.clear();
    }
}
