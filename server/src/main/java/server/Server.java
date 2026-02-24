package server;

import dataaccess.MemoryUserDAO;
import dataaccess.MemoryGameDAO;
import io.javalin.*;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import service.GameService;
import service.UserService;


public class Server {

    private final Javalin javalin;

    public Server() { // how do i pass in all the services
        MemoryUserDAO userDAO = new MemoryUserDAO();
        MemoryGameDAO gameDAO = new MemoryGameDAO();

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
//                .post("/game", this::newGame)
//                .put("/game", this::joinGame)
//                .delete("/db", this::deleteGames)
                .exception(RuntimeException.class, this::exceptionHandler);

        // Register your endpoints and exception handlers here.

    }

    private void exceptionHandler(RuntimeException e, @NotNull Context context) {
        context.status(400);
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
