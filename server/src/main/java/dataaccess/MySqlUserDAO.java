package dataaccess;

import exceptions.DataBaseException;
import model.Auth;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlUserDAO implements UserDataAccess {

    public MySqlUserDAO() throws DataBaseException {
        try {
            configureDatabase();
        } catch (DataAccessException ex) {
            throw new DataBaseException(String.format("Unable to configure database: %s", ex.getMessage()), 400);
        }
    }

    public User register(User user) throws DataBaseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, user.username());
                String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
                ps.setString(2, hashedPassword);
                ps.setString(3, user.email());
                ps.executeUpdate();
            }
        } catch (SQLException | DataAccessException ex) {
            throw new DataBaseException(String.format("unable to insert user : %s", ex.getMessage()), 400);
        }
        return user;
    }

    public void logout(String auth) throws DataBaseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "DELETE FROM auths WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, auth);
                ps.executeUpdate();
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataBaseException(String.format("unable to logout : %s", ex.getMessage()), 400);
        }
    }

    public Auth authorization(Auth auth) throws DataBaseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO auths (authToken, username) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, auth.authToken());
                ps.setString(2, auth.username());
                ps.executeUpdate();
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataBaseException("unable to insert auth", 400);
        }
        System.out.println("AUTHTOKEN IN MYSQL AUTHORIZATION IS " + auth);
        return auth;
    }

    public User getUser(String username) {
        String password = "";
        String email = "";
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM users WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                var result = ps.executeQuery();
                if (result.next()) {
                    password = result.getString("password");
                    email = result.getString("email");
                    return new User(username, password, email);
                }
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataBaseException("unable to find user", 400);
        }
        return null;
    }

    public String getAuth(String auth) {
        try (Connection conn = DatabaseManager.getConnection()) { // fails when it tries to get a connection -- why ??
            var statement = "SELECT username FROM auths WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, auth);
                var result = ps.executeQuery();
                if (result.next()) {
                    return result.getString("username");
                }
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataBaseException(String.format("unable to get auth %s", ex.getMessage()), 400);
        }
        return null;
    }

    public void clearApp() {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE users";
            var statement2 = "TRUNCATE auths";
            try (PreparedStatement ps = conn.prepareStatement(statement); PreparedStatement ps2 = conn.prepareStatement(statement2)) {
                ps.executeUpdate();
                ps2.executeUpdate();
            }
        } catch (DataAccessException | SQLException ex) {
            throw new DataBaseException(String.format("unable to clear app : %s", ex.getMessage()), 400);
        }
    }

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
