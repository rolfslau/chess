package server;

import service.GameService;
import exceptions.DoesNotExistException;
import io.javalin.http.Context;
import com.google.gson.Gson;
import Things.Game;

import java.util.Collection;

public class GameHandler {

    private final GameService service;

    public GameHandler(GameService service) {
        this.service = service;
    }

    public void listGames(Context ctx) throws DoesNotExistException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        Collection<Game> result = service.listGames(authToken);
        ctx.result(new Gson().toJson(result));
    }
}
