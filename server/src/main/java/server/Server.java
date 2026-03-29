package server;

import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import service.GameService;
import service.UserService;


public class Server {

    private final Javalin javalin;

    public Server() { // how do i pass in all the services
        UserDataAccess userDAO = new MySqlUserDAO();
        GameDataAccess gameDAO = new MySqlGameDAO();

        UserService userService = new UserService(userDAO);
        GameService gameService = new GameService(gameDAO, userDAO);

        UserHandler userHandler = new UserHandler(userService);
        GameHandler gameHandler = new GameHandler(gameService);

        // var server = new Server(service).run(port);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", userHandler::register)
                .post("/session", userHandler::login)
                .delete("/session", userHandler::logout)
                .get("/game", gameHandler::listGames)
                .post("/game", gameHandler::newGame)
                .put("/game", gameHandler::joinGame)
                .delete("/db", gameHandler::clearApp)
                .exception(RuntimeException.class, this::exceptionHandler)
                .ws("/ws", ws -> {
                    ws.onConnect(webSocketHandler);
                    ws.onMessage(webSocketHandler);
                    ws.onClose(webSocketHandler);
                });

        // Register your endpoints and exception handlers here.

    }

    private void exceptionHandler(RuntimeException e, @NotNull Context context) {
        context.status(500);
        String json = "{\"message\" : \"error" + e.getMessage() + "\"}";
        context.result(json);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
