package service;
import dataaccess.MemoryUserDAO;
import Model.User;
import Model.Auth;
import exceptions.AlreadyExistsException;
import exceptions.DoesNotExistException;

import java.util.Objects;
import java.util.UUID;


public class UserService {

    private final MemoryUserDAO dataAccess;

    public UserService(MemoryUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Auth register(User user) throws AlreadyExistsException {
        if (dataAccess.getUser(user.username()) != null) {
            throw new AlreadyExistsException("Error: User previously registered");
        }
        dataAccess.register(user);
        String authToken = generateToken();
        Auth auth = new Auth(authToken, user.username());
        return dataAccess.authorization(auth);
    }

    public Auth login(User user) throws DoesNotExistException {
        if (dataAccess.getUser(user.username()) == null) {
            throw new DoesNotExistException("Error: No user with that username");
        }
        else if (!Objects.equals(dataAccess.getUser(user.username()).password(), user.password())) {
            throw new DoesNotExistException("Error: wrong password");
        }
        String authToken = generateToken();
        Auth auth = new Auth(authToken, user.username());
        return dataAccess.authorization(auth);
    }

    public String logout(String auth) throws DoesNotExistException {
        if (dataAccess.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: no user to logout");
        }
        return dataAccess.logout(auth);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
