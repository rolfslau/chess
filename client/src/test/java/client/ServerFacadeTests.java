package client;

import model.*;
import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        serverFacade = new ServerFacade("http://localhost:8080");
        System.out.println("Started test HTTP server on " + port);
    }

    @Test
    @Order(1)
    @DisplayName("register positive")
    public void registerSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Assertions.assertDoesNotThrow(() -> serverFacade.register(user));
    }

    @Test
    @Order(2)
    @DisplayName("register negative")
    public void registerFailure() {
        User user = new User("", "password", "email@gmail.com");
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.register(user));
    }

    @Test
    @Order(3)
    @DisplayName("login positive")
    public void loginSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        Assertions.assertNotNull(auth);
    }

    @Test
    @Order(4)
    @DisplayName("login negative")
    public void loginFailure() {
        User user = new User("NotAUser", "password", "email@gmail.com");
        Assertions.assertThrows(RuntimeException.class, ()-> serverFacade.login(user));
    }

    //create
    @Test
    @Order(5)
    @DisplayName("create game positive")
    public void createSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        CreateGameReq game = new CreateGameReq("test game");
        int id = serverFacade.createGame(game, authToken);
        Assertions.assertEquals(1, id);
    }

    @Test
    @Order(6)
    @DisplayName("create game negative")
    public void createFailure() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        CreateGameReq game = new CreateGameReq("test game");
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.createGame(game, null));
    }

    //join

    @Test
    @Order(7)
    @DisplayName("join game positive")
    public void joinSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        CreateGameReq newGame = new CreateGameReq("new_game");
        int id = serverFacade.createGame(newGame, authToken);
        JoinGameReq game = new JoinGameReq("WHITE", id);
        serverFacade.joinGame(game, authToken);
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.joinGame(game, authToken));
    }

    @Test
    @Order(8)
    @DisplayName("join game negative")
    public void joinFailure() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        JoinGameReq game = new JoinGameReq("BLACK", 1);
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.joinGame(game, ""));
    }

    //list

    @Test
    @Order(9)
    @DisplayName("list game positive")
    public void listSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        Assertions.assertDoesNotThrow(() -> serverFacade.listGames(authToken));
    }

    @Test
    @Order(10)
    @DisplayName("list game negative")
    public void listFailure() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.listGames(""));
    }

    //logout

    @Test
    @Order(11)
    @DisplayName("logout positive")
    public void logoutSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        serverFacade.logout(authToken);
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.listGames(authToken));
    }

    @Test
    @Order(12)
    @DisplayName("logout negative")
    public void logoutFailure() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        CreateGameReq game = new CreateGameReq("new game");
        serverFacade.logout(authToken);
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.createGame(game, authToken));
    }


    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

}
