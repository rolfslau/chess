package service;
import dataaccess.MemoryUserDAO;
import Things.User;
import exceptions.AlreadyExistsException;

public class UserService {

    private final MemoryUserDAO dataAccess;

    public UserService(MemoryUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }
    public User register(User user) throws AlreadyExistsException {
        if (dataAccess.getUser(user.username()) != null) {
            throw new AlreadyExistsException("User previously registered");
        }
        return dataAccess.register(user);
    }
}
