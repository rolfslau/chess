package dataaccess;

import model.Game;
import chess.ChessGame;

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
        Game game = new Game(ids, null, null, gameName, new ChessGame());
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
            game = new Game(game.gameID(), user, game.blackUsername(), game.gameName(), game.game());
            games.put(gameID, game);
        }
        else {
            games.remove(gameID);
            game = new Game(game.gameID(), game.whiteUsername(), user, game.gameName(), game.game());
            games.put(gameID, game);
        }
    }

    public void clearApp() {
        games.clear();
    }
}
