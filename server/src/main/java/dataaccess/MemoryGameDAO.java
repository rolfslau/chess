package dataaccess;

import Things.Game;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDAO implements GameDataAccess {

    final public ArrayList<Game> games = new ArrayList<>();

    public MemoryGameDAO() {}

    public Collection<Game> listGames() {
        return games;
    }

}
