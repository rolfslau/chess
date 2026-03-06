package dataaccess;

import exceptions.DataBaseException;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlGameDAO implements GameDataAccess{


    public static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
            `id` int NOT NULL AUTO_INCREMENT,
            `whiteUsername` VARCHAR(256),
            `blackUsername` VARCHAR(256),
            `gameName` VARCHAR(256) NOT NULL,
            `game` TEXT NOT NULL
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
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
}
