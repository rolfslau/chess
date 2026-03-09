package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exceptions.DataBaseException;
import model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlGameDAO implements GameDataAccess {

    public MySqlGameDAO() throws DataBaseException {
        try {
            configureDatabase();
        } catch (DataAccessException ex) {
            throw new DataBaseException(String.format("Unable to configure database: %s", ex.getMessage()), 400);
        }
    }

    public Collection<Game> listGames() throws DataBaseException {
        ArrayList<Game> games = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM TABLE games";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                var result = ps.executeQuery();
                while (result.next()) {
                    Game game = new Game(result.getInt("id"),
                            result.getString("whiteUsername"),
                            result.getString("blackUsername"),
                            result.getString("gameName"),
                            new Gson().fromJson(result.getString("game"), ChessGame.class)
                            );
                    games.add(game);
                }
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataBaseException(String.format("unable to listGames : %s", ex.getMessage()), 400);
        }
        return games;
    }

    public int newGame(String gameName) {
        var statement = "INSERT INTO games VALUES (?, ?, ?, ?)";
        String game = new Gson().toJson(new ChessGame());
        return executeUpdate(statement, null, null, gameName, game);
    }

    public Game getGame(int gameID) {
        Game game = null;
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM TABLE games WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                var result = ps.executeQuery();
                if (result.next()) {
                    game = new Game(gameID, result.getString("whiteUsername"),
                            result.getString("blackUsername"),
                            result.getString("gameName"),
                            new Gson().fromJson(result.getString("game"), ChessGame.class)
                            );
                }
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataBaseException(String.format("unable to retrieve game : %s", ex.getMessage()), 400);
        }
        return game;
    }

    public void joinGame(String user, String color, int gameID) {
        var statement = "";
        if (Objects.equals(color, "WHITE")) {
            statement = "UPDATE TABLE games SET whiteUsername=? WHERE gameID=?";
        }
        else {
            statement = "UPDATE TABLE games SET blackUsername=? WHERE gameID=?";
        }
        executeUpdate(statement, user, gameID);
    }

    public void clearApp() {

    }

    public static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
            `id` int NOT NULL AUTO_INCREMENT,
            `whiteUsername` VARCHAR(256),
            `blackUsername` VARCHAR(256),
            `gameName` VARCHAR(256) NOT NULL,
            `game` TEXT NOT NULL,
            PRIMARY KEY(`id`)
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """ // game will be a json object !!!
    };

    public static void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataBaseException(String.format("Unable to configure database: %s", ex.getMessage()), 400);
        }
    }

    private int executeUpdate(String statement, Object... params) throws DataBaseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                } // this would give me the auto-generated key (for example in createGame)

                return 0;
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataBaseException(String.format("unable to update database: %s, %s", statement, e.getMessage()), 400);
        }
    }
}
