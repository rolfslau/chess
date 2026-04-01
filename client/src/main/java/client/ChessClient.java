package client;

import java.util.Objects;
import java.util.Scanner;

import chess.ChessBoard;
import model.Auth;
import model.CreateGameReq;
import model.JoinGameReq;
import model.User;
import websocket.commands.Notification;

import static ui.EscapeSequences.*;

// how do I keep the auth token?
// can two people log in from the same terminal? -- open two terminals
// if not how do they play each other?
// can you explain the server facade from pet shop -- make request body
// why in pet shop do they create a pet object instead of just passing the values they need
// --- check how I did it in my server, if I deserialized from an object then it needs to be an object on this end

public class ChessClient implements NotificationHandler {


    private final ServerFacade server;
    private State state = State.SIGNEDOUT;
    Scanner scanner = new Scanner(System.in);
    private String currAuth;
    private String currUser;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);

    }

    public void run() {
        System.out.print(RESET_BG_COLOR);
        System.out.print("✨welcome to chess!!✨\n");


        var result = "";
        while (!result.equals("quit")) {
            System.out.print(help());
            System.out.print("\n >>> ");
            String line = scanner.nextLine();
            if (state == State.SIGNEDIN) {
                result = evalIn(line);
            }
            else {result = evalOut(line); }
            System.out.print(result);

        }
        System.out.println();
    }

    public String evalIn(String line) {
        return switch (line) {
            case "create" -> createGame();
            case "join" -> joinGame();
            case "list" -> listGames();
            case "observe" -> observeGame();
            case "logout" -> logout();
            default -> "";
        };
    }

    public String evalOut(String line) {
        return switch (line) {
            case "register" -> register();
            case "login" -> login();
            case "quit" -> "quit";
            default -> "";
        };
    }

    public String register() {
        String username = "";
        String password = "";
        String email = "";
        System.out.print("username >>> ");
        username = scanner.nextLine();
        if (Objects.equals(username, "")) {
            System.out.print("\n\uD83D\uDEA8invalid username\uD83D\uDEA8\n\n");
            return "";
        }
        System.out.print("password >>> ");
        password = scanner.nextLine();
        if (Objects.equals(password, "")) {
            System.out.print("\n\uD83D\uDEA8invalid password\uD83D\uDEA8\n\n");
            return "";
        }
        System.out.print("email >>> ");
        email = scanner.nextLine();
        if (Objects.equals(email, "")) {
            System.out.print("\n\uD83D\uDEA8invalid email\uD83D\uDEA8\n\n");
            return "";
        }
        try {
            User user = new User(username, password, email);
            String authToken = server.register(user);
            currAuth = authToken;
            state = State.SIGNEDIN;
        } catch (RuntimeException e) {
            System.out.print("\n\uD83D\uDEA8username already taken\uD83D\uDEA8\n\n");
            return "";
        }
        return String.format("\nsuccessfully registered user \"%s\" !!\n\n", username);
    }

    public String login() {
        // make sure to get the auth token back
        String returner = "";
        System.out.print("username >>> ");
        String username = scanner.nextLine();

        System.out.print("password >>> ");
        String password = scanner.nextLine();
        try {
            User user = new User(username, password, null);
            Auth auth = server.login(user);
            currAuth = auth.authToken();
            currUser = auth.username();
            state = State.SIGNEDIN;
            returner = String.format("successfully logged in user \"%s\" !!\n\n", username);
        } catch (RuntimeException e) {
            System.out.print("\n\uD83D\uDEA8username or password incorrect\uD83D\uDEA8\n\n");
        }
        return returner;
    }

    public String createGame() {

        System.out.print("game name >>> ");
        String gameName = scanner.nextLine();
        if (Objects.equals(gameName, "")) {
            System.out.print("\n\uD83D\uDEA8invalid game name\uD83D\uDEA8\n\n");
            return "";
        }
        CreateGameReq game = new CreateGameReq(gameName);
        Integer id = server.createGame(game, currAuth);
        return String.format("game \"%s\" created\n\n", gameName);
    }

    public String joinGame() {
        System.out.print("game id >>> ");
        String gameNum = scanner.nextLine();

        System.out.print("color >>> ");
        String color = scanner.nextLine();
        try {
            int gameID = Integer.parseInt(gameNum);
            JoinGameReq game = new JoinGameReq(color.toUpperCase(), gameID);
            server.joinGame(game, currAuth);
            ChessBoard board = new ChessBoard();
            board.resetBoard();
            new DrawingChess(board, color.toUpperCase());
            return String.format("game %d joined as %s\n\n", gameID, color);
        } catch (RuntimeException e) {
            System.out.print("\n\uD83D\uDEA8invalid game id or color (already taken or not black/white)\uD83D\uDEA8\n\n");
        }
            return "";
        }


    public String listGames() {
        server.listGames(currAuth);
        return "\nall games listed!\n\n";
    }

    public String observeGame() {
        int gameID = 0;
        try {
            System.out.print("game id >>> ");
            gameID = Integer.parseInt(scanner.nextLine());
        } catch (RuntimeException e) {
            System.out.print("\n\uD83D\uDEA8invalid gameID\uD83D\uDEA8\n\n");
            return "";
        }
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        new DrawingChess(board, "WHITE");
        return String.format("watching game %d\n\n", gameID);
    }

    public String logout() {
        // what goes here? should I be saving the username of whoever is logged in?
        server.logout(currAuth);
        state = State.SIGNEDOUT;
        return "\nsuccessfully logged out\n\n";
    }

    public void notify(Notification notification) {
        System.out.println(notification.message());
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
