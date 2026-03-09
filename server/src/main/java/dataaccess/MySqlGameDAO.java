package dataaccess;

import exceptions.DataBaseException;
import model.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

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

    public Collection<Game> listGames() {
    }

    public int newGame(String gameName) {
    }

    public Game getGame(int gameID) {
    }

    public void joinGame(String user, String color, int gameID) {}

    public void clearApp() {

    }

    public static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
            `id` int NOT NULL AUTO_INCREMENT,
            `whiteUsername` VARCHAR(256),
            `blackUsername` VARCHAR(256),
            `gameName` VARCHAR(256) NOT NULL,
            `game` TEXT NOT NULL
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
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param == null) ps.setNull(i + 1, NULL);
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
