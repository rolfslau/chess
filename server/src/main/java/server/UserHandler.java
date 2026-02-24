package server;

import exceptions.AlreadyExistsException;
import exceptions.DoesNotExistException;
import io.javalin.http.Context;
import com.google.gson.Gson;
import service.UserService;
import Things.User;
import Things.Auth;

public class UserHandler { // should this inherit from server?

    private final UserService service;

    public UserHandler(UserService service) {
            this.service = service;
        }

    public void register(Context ctx) throws AlreadyExistsException {
        // check for errors
        User user = new Gson().fromJson(ctx.body(), User.class);
        Auth auth = service.register(user);
        ctx.result(new Gson().toJson(auth));
    }

    public void login(Context ctx) throws DoesNotExistException {
        User user = new Gson().fromJson(ctx.body(), User.class);
        Auth auth = service.login(user);
        ctx.result(new Gson().toJson(auth));
    }

    public void logout(Context ctx) throws DoesNotExistException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        String result = service.logout(authToken);
        ctx.result(new Gson().toJson(result));
    }
}
