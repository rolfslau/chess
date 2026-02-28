package dataaccess;

import model.Auth;
import model.User;

public interface UserDataAccess {

    User register(User user);

    String logout(String auth);

    Auth authorization(Auth auth);

    User getUser(String username);

    String getAuth(String auth);

    void clearApp();
}
