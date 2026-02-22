package dataaccess;

import java.util.HashMap;
import Things.User;

public class MemoryUserDAO implements UserDataAccess {

    public MemoryUserDAO() {}

    final public HashMap<String, User> users = new HashMap<>();

    public User register(User user) {
        user = new User(user.username(), user.password(), user.email());

        users.put(user.username(), user);
        return user;

    }

    public User getUser(String username) {
        return users.get(username);
    }
}
