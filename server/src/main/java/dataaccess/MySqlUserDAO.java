package dataaccess;

import exceptions.DataBaseException;
import model.Auth;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlUserDAO implements UserDataAccess {

    public MySqlUserDAO() throws DataAccessException {
        configureDatabase();
    }

    public User register(User user) {}

    public String logout(String auth) {}

    public Auth authorization(Auth auth) {}

    public User getUser(String username) {}

    public String getAuth(String auth) {}

    public void clearApp() {}

    public static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS users (
            `username` varchar(256) NOT NULL,
            `password` varchar(256) NOT NULL,
            `email` varchar(256) NOT NULL
            PRIMARY KEY (`username`)
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS auths (
            `authToken` varchar(256) NOT NULL,
            `username` varchar(256) NOT NULL,
            PRIMARY KEY (`authToken`),
            index(username)
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    public static void configureDatabase() throws DataBaseException, DataAccessException {
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
