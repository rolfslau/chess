package client;

import java.util.Objects;
import java.util.Scanner;

import chess.ChessBoard;
import chess.ChessPiece;
import model.Auth;
import model.CreateGameReq;
import model.JoinGameReq;
import model.User;
import server.ServerFacade;

import static ui.EscapeSequences.*;

// how do I keep the auth token?
// can two people log in from the same terminal? -- open two terminals
// if not how do they play each other?
// can you explain the server facade from pet shop -- make request body
// why in pet shop do they create a pet object instead of just passing the values they need
// --- check how I did it in my server, if I deserialized from an object then it needs to be an object on this end

public class ChessClient {

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
            case "list" -> listGames();
            case "observe" -> observeGame();
            case "logout" -> logout();
            case "quit" -> "quit";
            default -> "";
        };
    }

    public String register() {
        boolean keep_going = true;
        String username = "";
        String password = "";
        String email = "";
        while (keep_going) {
            System.out.print("username >>> ");
            username = scanner.nextLine();
            if (Objects.equals(username, "")) {
                System.out.print("invalid username\n");
                continue;
            }
            keep_going = false;
        }
        keep_going = true;
        while (keep_going) {
            System.out.print("password >>> ");
            password = scanner.nextLine();
            if (Objects.equals(password, "")) {
                System.out.print("invalid password\n");
                continue;
            }
            keep_going = false;
        }
        keep_going = true;
            while (keep_going) {
                System.out.print("email >>> ");
                email = scanner.nextLine();
                if (Objects.equals(email, "")) {
                    System.out.print("invalid email\n");
                    continue;
                }
                keep_going = false;
            }
        User user = new User(username, password, email);
        server.register(user);
        return String.format("successfully registered user %s !!\n\n", username);
    }

    public String login() {
        // make sure to get the auth token back
        String returner = "";
        boolean keep_going = true;
        while (keep_going) {
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
                returner = String.format("successfully logged in user %s !!\n\n", username);
            } catch (RuntimeException e) {
                System.out.print("username or password incorrect\n");
                continue;
            }
            keep_going = false;
        }
        return returner;
    }

    public String createGame() {
        boolean keep_going = true;
        String gameName = "";
        while (keep_going) {
            System.out.print("game name >>> ");
            gameName = scanner.nextLine();
            if (gameName == "") {
                System.out.print("invalid game name\n");
                continue;
            }
            keep_going = false;
        }
        CreateGameReq game = new CreateGameReq(gameName);
        Integer id = server.createGame(game, currAuth);
        return String.format("game %s created with id: %d\n\n", gameName, id);
    }

    public String joinGame() {
        System.out.print("game id >>> ");
        int gameID = Integer.parseInt(scanner.nextLine());

        System.out.print("color >>> ");
        String color = scanner.nextLine();
        JoinGameReq game = new JoinGameReq(color.toUpperCase(), gameID);
        server.joinGame(game, currAuth);
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        new DrawingChess(board, color.toUpperCase());
        return String.format("game %d joined as %s\n\n", gameID, color);
    }

    public String listGames() {
        server.listGames(currAuth);
        return "\nall games listed!\n\n";
    }

    public String observeGame() {
        System.out.print("game id >>> ");
        int gameID = Integer.parseInt(scanner.nextLine());
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
