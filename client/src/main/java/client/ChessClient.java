package client;

import java.util.*;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import model.*;
import websocket.commands.*;

import static ui.EscapeSequences.*;

// to do:
// figure out why my tests are not passing -- why am I not sending error notifications when I literally am
public class ChessClient implements NotificationHandler {


    private final ServerFacade server;
    private final WebSocketFacade ws;

    private State state = State.SIGNEDOUT;
    private State gameState = State.SIGNEDOUT;

    Scanner scanner = new Scanner(System.in);

    private String currAuth;
    private String currUser;
    private boolean player = false;
    int currGameID = 0;
    String currColor = "";
    Game currGame = null;

    private final Map<String, Integer> coords = Map.of(
            "A", 1,
            "B", 2,
            "C", 3,
            "D", 4,
            "E", 5,
            "F", 6,
            "G", 7,
            "H", 8
    );

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        ws = new WebSocketFacade(serverUrl, this);
    }

    public void run() {
        System.out.print(RESET_BG_COLOR);
        System.out.print("✨welcome to chess!!✨\n");
        System.out.print(help());

        var result = "";
        while (!result.equals("quit")) {
//            System.out.print(help());
            System.out.print("\n");
            String line = scanner.nextLine();
            if (state == State.SIGNEDIN) {
                if (gameState == State.SIGNEDIN && player) {
                    result = evalGamePlay(line);
                }
                else if (gameState == State.SIGNEDIN) {
                    result = evalGameObserve(line);
                }
                else {
                    result = evalIn(line);
                }
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
            case "help" -> help();
            default -> "";
        };
    }

    public String evalOut(String line) {
        return switch (line) {
            case "register" -> register();
            case "login" -> login();
            case "quit" -> "quit";
            case "help" -> help();
            default -> "";
        };
    }

    public String evalGamePlay(String line) {
        return switch (line) {
            case "reload" -> reload();
            case "leave" -> leave();
            case "move" -> makeMove();
            case "resign" -> resign();
            case "highlight" -> highlight();
            case "help" -> help();
            default -> "";
        };
    }

    public String evalGameObserve(String line) {
        return switch (line) {
            case "reload" -> reload();
            case "leave" -> leave();
            case "move", "resign" -> "you are an observer!!";
            case "highlight" -> highlight();
            case "help" -> help();
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
            currUser = user.username();
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
            ws.joinGame(currAuth, game, currUser);
            player = true;
            this.currGameID = gameID;
            this.currColor = color.toUpperCase();
            // don't replace this, do both (ws as well)
            // client doesn't have to say that they are connecting as white because the server can derive
            // for websocket ^^
            // server uses the gameid and auth token to look up the player's role they joined as
            GameID id = new GameID(currGameID);
            ChessBoard board = server.getGame(currAuth, id).game().getBoard();
//            currGame = server.getGame(currAuth, id).game();
//            new DrawingChess(board, color.toUpperCase(), new ArrayList<>(), null);
            gameState = State.SIGNEDIN;
            return String.format("game %d joined as %s\n\n", gameID, color);
        } catch (RuntimeException e) {
            System.out.print("\n\uD83D\uDEA8invalid game id or color (already taken or not black/white)\uD83D\uDEA8\n\n");
        }
            return "";
    }

    public String resign() {
        try {
            System.out.print("are you sure you want to resign? [y/n] >>> ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("y")) {
                ResignCommand resign = new ResignCommand(UserGameCommand.CommandType.RESIGN, currAuth, currGameID, currUser);
                ws.resign(resign);
                currGame = new Game(currGame.gameID(), currGame.whiteUsername(), currGame.blackUsername(),
                        currGame.gameName(), "false", currGame.game());
            } else if (confirm.equalsIgnoreCase("n")) {
                return "ok!";
            }
        } catch(RuntimeException ex) {
            return String.format("non valid response %s", ex.getMessage());
        }
        return "you resigned";
    }

    public String leave() {
        try {
            LeaveCommand command = new LeaveCommand(UserGameCommand.CommandType.LEAVE, currAuth, currUser, currGameID, currColor);
            ws.leave(command);
            currGameID = 0;
            currColor = "";
            gameState = State.SIGNEDOUT;
            currGame = null;
            return "\nyou have left the game\n\n";
        } catch (RuntimeException e) {
            System.out.print("\nyou were not able to leave the game\n\n");
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
            currGameID = gameID;
            currColor = "WHITE";
        } catch (RuntimeException e) {
            System.out.print("\n\uD83D\uDEA8invalid gameID\uD83D\uDEA8\n\n");
            return "";
        }
        GameID gameNum = new GameID(currGameID);
        ChessBoard board = server.getGame(currAuth, gameNum).game().getBoard();
        JoinGameReq game = new JoinGameReq("AN OBSERVER", gameID);
        ws.joinGame(currAuth, game, currUser);
        gameState = State.SIGNEDIN;
        return String.format("watching game %d\n\n", gameID);
    }

    public String highlight() {
        String pos = "";
        try {
           System.out.print("piece position >>> ");
           pos = scanner.nextLine().toUpperCase();
           int col = coords.get(String.valueOf(pos.charAt(0)));
           ChessPosition piecePos = new ChessPosition(Integer.parseInt(String.valueOf(pos.charAt(1))), col);
           GameID gameID = new GameID(currGameID);
           Collection<ChessMove> moves = currGame.game().validMoves(piecePos, currGame.game().getBoard());
           Collection<ChessPosition> endMoves = moves.stream().map(ChessMove::getEndPosition).toList();
           new DrawingChess(currGame.game().getBoard(), currColor, endMoves, piecePos);
           return String.format("\npotential moves for piece at %s\n\n", pos);
        } catch(RuntimeException ex) {
            System.out.printf("\nunable to highlight moves for piece at %s\n\n", pos);
        }
        return "";
    }

    public String logout() {
        // what goes here? should I be saving the username of whoever is logged in?
        server.logout(currAuth);
        state = State.SIGNEDOUT;
        return "\nsuccessfully logged out\n\n";
    }

    public String reload() {
        try {
            // should I be calling websocket here to reload? that seemed to fix my other issue
            GameID gameId = new GameID(currGameID);
            ChessBoard board = server.getGame(currAuth, gameId).game().getBoard();
            new DrawingChess(currGame.game().getBoard(), currColor, new ArrayList<>(), null);
            return "";
        } catch(RuntimeException ex) {
            return"\ncannot reload game\n\n";
        }
    }

    public String makeMove() {
        String start = "";
        String end = "";
        if (Objects.equals(currGame.playing(), "false")) {
            return "this game already ended!";
        }
        try {
            System.out.print("starting position >>> ");
            start = scanner.nextLine().toUpperCase();
            int col1 = coords.get(String.valueOf(start.charAt(0)));
            ChessPosition pos1 = new ChessPosition(Integer.parseInt(String.valueOf(start.charAt(1))), col1);
            System.out.print("ending position >>> ");
            end = scanner.nextLine().toUpperCase();
            int col2 = coords.get(String.valueOf(end.charAt(0)));
            ChessPosition pos2 = new ChessPosition(Integer.parseInt(String.valueOf(end.charAt(1))), col2);
            ChessMove move = new ChessMove(pos1, pos2, null);
            MakeMoveCommand makeMove = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE, currAuth, currUser, currGameID, move);
            ws.makeMove(makeMove);
            return String.format("\nyou moved from %s to %s\n\n", start, end);
        } catch(RuntimeException ex) {
            return "\ninvalid move -- not your turn or not a legal move\n\n";
        }
    }

    public void notify(ServerMessage notification) {
        System.out.println(notification.getMessage());
        if (notification.getMessage().contains("resigned")) {
            currGame = new Game(currGame.gameID(), currGame.whiteUsername(), currGame.blackUsername(),
                    currGame.gameName(), "false", currGame.game());
        }
    }

    public void notifyReload(ReloadNotification notification) {
        Game game = new Gson().fromJson(notification.getGame(), Game.class);
        ChessBoard board = game.game().getBoard();
        currGame = game;
        new DrawingChess(board, currColor, new ArrayList<>(), null);
    }

    public void notifyError(ErrorNotification notification) {
        System.out.println(notification.getErrorMessage());
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
            if (gameState == State.SIGNEDIN) {
                return """
                        help
                        reload (board)
                        leave
                        move
                        resign
                        highlight (legal moves)
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
}
