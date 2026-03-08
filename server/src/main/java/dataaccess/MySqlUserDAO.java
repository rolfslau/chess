package dataaccess;

import com.google.gson.Gson;
import exceptions.DataBaseException;
import model.Auth;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlUserDAO implements UserDataAccess {

    public MySqlUserDAO() throws DataAccessException {
        configureDatabase();
    }

    public User register(User user) throws DataBaseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, user.username());
                String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
                ps.setString(2, hashedPassword);
                ps.setString(3, user.email());
                int result = ps.executeUpdate();
                if (result != 1) {
                    throw new DataBaseException("insert unsuccessful", 400);
                }
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataBaseException(String.format("unable to insert user : %s", ex.getMessage()), 400);
        }
        return user;
    }

    public String logout(String auth) {}

    public Auth authorization(Auth auth) {}

    public User getUser(String username) {}

    public String getAuth(String auth) {}

    public void clearApp() {}

    public static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS users (
            `username` VARCHAR(256) NOT NULL,
            `password` VARCHAR(256) NOT NULL,
            `email` VARCHAR(256) NOT NULL,
            PRIMARY KEY (`username`)
            )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """,
            """
            CREATE TABLE IF NOT EXISTS auths (
            `authToken` VARCHAR(256) NOT NULL,
            `username` VARCHAR(256) NOT NULL,
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
