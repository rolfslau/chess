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

public class GameHandler {

    private final GameService service;

    public GameHandler(GameService service) {
        this.service = service;
    }

    public void listGames(Context ctx) throws DoesNotExistException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        HashMap<Integer, Game> result = service.listGames(authToken);
        ctx.result(new Gson().toJson(result));
    }

    public void newGame(Context ctx) throws DoesNotExistException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        String gameName = new Gson().fromJson(ctx.body(), createGameReq.class).gameName();
        int result = service.newGame(authToken, gameName);
        ctx.result(new Gson().toJson(result));
    }

    public void joinGame(Context ctx) throws DoesNotExistException, AlreadyExistsException {
        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        Model.JoinGameReq body = new Gson().fromJson(ctx.body(), JoinGameReq.class);
        String color = body.playerColor();
        int gameID = body.gameID();
        String result = service.joinGame(authToken, color, gameID);
        ctx.result(new Gson().toJson(result));
    }

    public void clearApp(Context ctx) {
        service.clearApp();
    }
}
