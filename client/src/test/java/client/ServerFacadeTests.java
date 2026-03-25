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
    @DisplayName("register positive")
    public void registerSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Assertions.assertDoesNotThrow(() -> serverFacade.register(user));
    }

    @Test
    @DisplayName("register negative")
    public void registerFailure() {
        User user = new User("", "password", "email@gmail.com");
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.register(user));
    }

    @Test
    @DisplayName("login positive")
    public void loginSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        Assertions.assertNotNull(auth);
    }

    @Test
    @DisplayName("login negative")
    public void loginFailure() {
        User user = new User("NotAUser", "password", "email@gmail.com");
        Assertions.assertThrows(RuntimeException.class, ()-> serverFacade.login(user));
    }

    //create
    @Test
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
    @DisplayName("join game positive")
    public void joinSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        JoinGameReq game = new JoinGameReq("WHITE", 1);
        serverFacade.joinGame(game, authToken);
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.joinGame(game, authToken));
    }

    @Test
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
    @DisplayName("list game positive")
    public void listSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        Assertions.assertDoesNotThrow(() -> serverFacade.listGames(authToken));
    }

    @Test
    @DisplayName("list game negative")
    public void listFailure() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.listGames(""));
    }

    //logout

    @Test
    @DisplayName("logout positive")
    public void logoutSuccess() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        serverFacade.logout(authToken);
        Assertions.assertThrows(RuntimeException.class, () -> serverFacade.listGames(authToken));
    }

    @Test
    @DisplayName("logout negative")
    public void logoutFailure() {
        User user = new User("test", "password", "email@gmail.com");
        Auth auth = serverFacade.login(user);
        String authToken = auth.authToken();
        Assertions.assertDoesNotThrow(() -> serverFacade.listGames(authToken));
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
