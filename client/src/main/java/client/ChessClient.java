package client;

import java.util.Scanner;

import model.CreateGameReq;
import model.User;
import server.ServerFacade;

public class ChessClient {

    private final ServerFacade server;
    private State state = State.SIGNEDOUT;
    Scanner scanner = new Scanner(System.in);

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public void run() {
        System.out.print("✨welcome to chess!!✨");
        System.out.print("type \"help\" to start :)");
        System.out.print(help());


        var result = "";
        while (!result.equals("quit")) {
            System.out.print("\n >>> ");
            String line = scanner.nextLine();

            result = eval(line);
            System.out.print(result);
        }
        System.out.println();
    }

    public String eval(String line) {
        return switch (line) {
            case "register" -> register();
            case "login" -> login();
            case "create" -> createGame();
            case "join" -> joinGame();
            case "observe" -> observeGame();
            case "logout" -> logout();
            case "quit" -> "quit";
            default -> help();
        };
    }

    public String register() {
        System.out.print("username >>> ");
        String username = scanner.nextLine();

        System.out.print("password >>> ");
        String password = scanner.nextLine();

        System.out.print("email >>> ");
        String email = scanner.nextLine();

        User user = new User(username, password, email);
        server.register(user);
        return String.format("successfully registered user %s !!", username);
    }

    public String login() {
        System.out.print("username >>> ");
        String username = scanner.nextLine();

        System.out.print("password >>> ");
        String password = scanner.nextLine();

        User user = new User(username, password, null);
        server.login(user);
        state = State.SIGNEDIN;
        return String.format("successfully logged in user %s !!", username);
    }

    String createGame() {
        System.out.print("game name >>> ");
        String gameName = scanner.nextLine();
        CreateGameReq game = new CreateGameReq(gameName);
        return String.format("game created: %s", gameName);
    }



    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    register
                    login
                    help
                    quit
                    """;
        }
        else {
            return """
                    create (a game)
                    join (a game)
                    list (all games)
                    observe (a game)
                    help
                    logout
                    """;
        }
    }
}
