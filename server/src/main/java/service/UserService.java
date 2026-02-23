package service;
import dataaccess.MemoryUserDAO;
import Things.User;
import Things.Auth;
import exceptions.AlreadyExistsException;
import java.util.UUID;


public class UserService {

    private final MemoryUserDAO dataAccess;

    public UserService(MemoryUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Auth register(User user) throws AlreadyExistsException {
        if (dataAccess.getUser(user.username()) != null) {
            throw new AlreadyExistsException("User previously registered");
        }
        dataAccess.register(user);
        String authToken = generateToken();
        Auth auth = new Auth(authToken, user.username());
        return dataAccess.authorization(auth);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
