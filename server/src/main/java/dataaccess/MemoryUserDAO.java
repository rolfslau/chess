package dataaccess;

import java.util.HashMap;
import model.User;
import model.Auth;

public class MemoryUserDAO implements UserDataAccess {

    public MemoryUserDAO() {}

    final public HashMap<String, User> users = new HashMap<>();
    final public HashMap<String, String> auths = new HashMap<>();

    public User register(User user) {
        user = new User(user.username(), user.password(), user.email());

        users.put(user.username(), user);
        return user;

    }

    public void logout(String auth) {
        auths.remove(auth);
    }

    public Auth authorization(Auth auth) {
        auths.put(auth.authToken(), auth.username());
        return auth;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public String getAuth(String auth) {
        return auths.get(auth);
    }

    public void clearApp() {
        users.clear();
        auths.clear();
    }
}
