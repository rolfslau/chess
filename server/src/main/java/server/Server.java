package server;

import dataaccess.MemoryUserDAO;
import io.javalin.*;
import service.UserService;

public class Server {

    private final Javalin javalin;

    public Server() { // how do i pass in all the services
        MemoryUserDAO userDAO = new MemoryUserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userHandler = new UserHandler(userService);

        // var server = new Server(service).run(port);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", userHandler::register)
                .post("/session", this::login)
                .delete("/session", this::logout)
                .get("/game", this::listGames)
                .post("/game", this::newGame)
                .put("/game", this::joinGame)
                .delete("/db", this::deleteGames)
                .exception(ResponseException.class, this::exceptionHandler);

        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
