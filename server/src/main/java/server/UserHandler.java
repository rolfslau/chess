package server;

import dataaccess.MemoryUserDAO;
import io.javalin.http.Context;
import com.google.gson.Gson;
import service.UserService;
import Things.User;

public class UserHandler { // should this inherit from server?

    private final UserService service;

    public UserHandler(UserService service) {
            this.service = service;
        }

    public void register(Context ctx) {
        // check for errors
        User user = new Gson().fromJson(ctx.body(), User.class);
        user = service.register(user);
        ctx.result(new Gson().toJson(user));
    }
}
