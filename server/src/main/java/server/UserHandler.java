package server;

import dataaccess.MemoryUserDAO;
import io.javalin.http.Context;
import com.google.gson.Gson;
import service.UserService;
import Things.User;

public class UserHandler {

    private final UserService service;

    public UserHandler() {
        this(new UserService(new MemoryUserDAO()));
    }

    public void register(Context ctx) {
        User user = new Gson().fromJson(ctx.body(), User.class);
        user = service.register(user);
        ctx.result(new Gson().toJson(user));
    }
}
