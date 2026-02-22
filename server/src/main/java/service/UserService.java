package service;
import dataaccess.MemoryUserDAO;
import Things.User;

public class UserService {

    private final MemoryUserDAO dataAccess;

    public UserService(MemoryUserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }
    public User register(User user) {
        return dataAccess.register(user);
    }
}
