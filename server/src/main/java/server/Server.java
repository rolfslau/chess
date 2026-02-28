package server;

import dataaccess.GameDataAccess;
import dataaccess.MemoryUserDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.UserDataAccess;
import io.javalin.*;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import service.GameService;
import service.UserService;


public class Server {

    private final Javalin javalin;

    public Server() { // how do i pass in all the services
        UserDataAccess userDAO = new MemoryUserDAO();
        GameDataAccess gameDAO = new MemoryGameDAO();

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
                .exception(RuntimeException.class, this::exceptionHandler);

        // Register your endpoints and exception handlers here.

    }

    private void exceptionHandler(RuntimeException e, @NotNull Context context) {
        context.status(500);
        String json = "{\"error\" : \"" + e.getMessage() + "\"}";
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
