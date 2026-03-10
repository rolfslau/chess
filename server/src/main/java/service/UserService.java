package service;
import dataaccess.UserDataAccess;
import model.User;
import model.Auth;
import exceptions.AlreadyExistsException;
import exceptions.DoesNotExistException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Objects;
import java.util.UUID;


public class UserService {

    private final UserDataAccess dataAccess;

    public UserService(UserDataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Auth register(User user) throws AlreadyExistsException, DoesNotExistException {
        if (dataAccess.getUser(user.username()) != null) {
            throw new AlreadyExistsException("Error: User previously registered", 403);
        }
        if (Objects.equals(user.username(), "") || Objects.equals(user.username(), null)) {
            throw new DoesNotExistException("Error: bad request", 400);
        }
        if (user.password() == null) {
            throw new DoesNotExistException("Error: bad request", 400);
        }
        dataAccess.register(user);
        String authToken = generateToken();
        Auth auth = new Auth(authToken, user.username());
        return dataAccess.authorization(auth);
    }

    public Auth login(User user) throws DoesNotExistException {
        if (user.username() == null) {
            throw new DoesNotExistException("Error: bad request", 400);
        }
        if (user.password() == null) {
            throw new DoesNotExistException("Error: bad request", 400);
        }
        User returnedUser = dataAccess.getUser(user.username());
        if (returnedUser == null) {
            throw new DoesNotExistException("Error: unauthorized", 401);
        }
        if (!BCrypt.checkpw(user.password(), returnedUser.password())) {
            throw new DoesNotExistException("Error: unauthorized", 401);
        }


        String authToken = generateToken();
        Auth auth = new Auth(authToken, user.username());
        return dataAccess.authorization(auth);
    }

    public void logout(String auth) throws DoesNotExistException {
        if (dataAccess.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: no user to logout", 401);
        }
        dataAccess.logout(auth);
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
