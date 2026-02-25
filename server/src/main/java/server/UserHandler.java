package server;

import exceptions.AlreadyExistsException;
import exceptions.DoesNotExistException;
import io.javalin.http.Context;
import com.google.gson.Gson;
import service.UserService;
import Model.User;
import Model.Auth;

import java.util.Map;

public class UserHandler { // should this inherit from server?

    private final UserService service;

    public UserHandler(UserService service) {
            this.service = service;
        }

    public void register(Context ctx) throws AlreadyExistsException {
        // check for errors
        User user = new Gson().fromJson(ctx.body(), User.class);
        try {
            Auth auth = service.register(user);
            ctx.result(new Gson().toJson(auth));
        }
        catch(AlreadyExistsException e) {
            ctx.status(403);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public void login(Context ctx) throws DoesNotExistException {
        User user = new Gson().fromJson(ctx.body(), User.class);
        try {
            Auth auth = service.login(user);
            ctx.result(new Gson().toJson(auth));
        }
        catch(DoesNotExistException e) {
            ctx.status(401); // make status part of the error?
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public void logout(Context ctx) throws DoesNotExistException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        try {
            service.logout(authToken);
            ctx.status(200);
        }
        catch(DoesNotExistException e) {
            ctx.status(401);
//            String result = "{\"message\": \"" + e.getMessage() + "\" }";
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }

    }
}
