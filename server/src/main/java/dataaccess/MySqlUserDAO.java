package dataaccess;

import model.Auth;
import model.User;

public class MySqlUserDAO implements UserDataAccess {

    public MySqlUserDAO() throws {}

    public User register(User user) {}

    public String logout(String auth) {}

    public Auth authorization(Auth auth) {}

    public User getUser(String username) {}

    public String getAuth(String auth) {}

    public void clearApp() {}

}
