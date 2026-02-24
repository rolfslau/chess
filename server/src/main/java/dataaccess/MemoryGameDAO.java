package dataaccess;

import Things.Game;
import chess.ChessGame;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDataAccess {

    int ids = 1;
    final public ArrayList<Game> games = new ArrayList<>();

    public MemoryGameDAO() {}

    public Collection<Game> listGames() {
        return games;
    }

    public int newGame(String gameName, String user) {
        Game game = new Game(ids, user, "", gameName, new ChessGame());
        games.add(game);
        return ids++;
    }

}
