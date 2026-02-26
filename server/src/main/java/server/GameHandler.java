package server;

import model.JoinGameReq;
import exceptions.AlreadyExistsException;
import service.GameService;
import exceptions.DoesNotExistException;
import io.javalin.http.Context;
import com.google.gson.Gson;
import model.Game;
import model.CreateGameReq;
import java.util.Collection;
import java.util.Map;

public class GameHandler {

    private final GameService service;

    public GameHandler(GameService service) {
        this.service = service;
    }

    public void listGames(Context ctx) throws DoesNotExistException {
        String authToken = ctx.header("authorization");
        try {
            Collection<Game> result = service.listGames(authToken);
            ctx.result(new Gson().toJson(Map.of("games",  result)));
        }
        catch(DoesNotExistException e) {
            ctx.status(e.errorCode);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public void newGame(Context ctx) throws DoesNotExistException {
        String authToken = ctx.header("authorization");
        String gameName = new Gson().fromJson(ctx.body(), CreateGameReq.class).gameName();
        try {
            int result = service.newGame(authToken, gameName);
            ctx.result(new Gson().toJson(Map.of("gameID", result)));
        }
        catch(DoesNotExistException e) {
            ctx.status(e.errorCode);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public void joinGame(Context ctx) throws DoesNotExistException, AlreadyExistsException {
//        String authToken = new Gson().fromJson(ctx.header("authorization"), String.class);
        String authToken = ctx.header("authorization");
        model.JoinGameReq body = new Gson().fromJson(ctx.body(), JoinGameReq.class);
        try {
            String color = body.playerColor();
            int gameID = body.gameID();
            service.joinGame(authToken, color, gameID);
        }
        catch(DoesNotExistException e) {
            ctx.status(e.errorCode);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
        catch(AlreadyExistsException e) {
            ctx.status(e.errorCode);
            ctx.result(new Gson().toJson(Map.of("message", e.getMessage())));
        }
    }

    public void clearApp(Context ctx) {
        service.clearApp();
    }
}
