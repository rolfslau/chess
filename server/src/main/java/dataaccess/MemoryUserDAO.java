package dataaccess;

import java.util.HashMap;
import Things.User;

public class MemoryUserDAO implements UserDataAccess {
    private int id = 1;
    final private HashMap<Integer, User> users = new HashMap<>();

    public User register(User user) {
        user = new User(id++, user.username(), user.password(), user.email());

        users.put(user.id(), user);
        return user;

    }
}
