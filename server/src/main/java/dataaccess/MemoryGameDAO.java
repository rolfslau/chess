package dataaccess;

import Things.Game;
import chess.ChessGame;

import java.util.HashMap;
import java.util.Objects;

public class MemoryGameDAO implements GameDataAccess {

    int ids = 1;
    final public HashMap<Integer, Game> games = new HashMap<>();

    public MemoryGameDAO() {}

    public HashMap<Integer, Game> listGames() {
        return games;
    }

    public int newGame(String gameName, String user) {
        Game game = new Game(ids, user, "", gameName, new ChessGame());
        games.put(ids, game);
        return ids++;
    }

    public Game getGame(int gameID) {
        return games.get(gameID);
    }

    public String joinGame(String user, String color, int gameID) {
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
        return "";
    }
}
