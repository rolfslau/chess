package server;

import Model.JoinGameReq;
import exceptions.AlreadyExistsException;
import service.GameService;
import exceptions.DoesNotExistException;
import io.javalin.http.Context;
import com.google.gson.Gson;
import Model.Game;
import Model.createGameReq;
import java.util.HashMap;
import java.util.Map;

public class GameHandler {

    private final GameService service;

    public GameHandler(GameService service) {
        this.service = service;
    }

    public void listGames(Context ctx) throws DoesNotExistException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        try {
            HashMap<Integer, Game> result = service.listGames(authToken);
            ctx.result(new Gson().toJson(result));
        }
        catch(DoesNotExistException e) {
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public void newGame(Context ctx) throws DoesNotExistException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        String gameName = new Gson().fromJson(ctx.body(), createGameReq.class).gameName();
        try {
            int result = service.newGame(authToken, gameName);
            ctx.result(new Gson().toJson(Map.of("gameID", result)));
        }
        catch(DoesNotExistException e) {
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public void joinGame(Context ctx) throws DoesNotExistException, AlreadyExistsException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        Model.JoinGameReq body = new Gson().fromJson(ctx.body(), JoinGameReq.class);
        try {
            String color = body.playerColor();
            int gameID = body.gameID();
            service.joinGame(authToken, color, gameID);
        }
        catch(RuntimeException e) {
            ctx.status(401);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public void clearApp(Context ctx) {
        service.clearApp();
    }
}
