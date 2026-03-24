package client;

import java.util.Scanner;

import server.ServerFacade;

public class ChessClient {

    private final ServerFacade server;
    private State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public void run() {
        System.out.print("✨welcome to chess!!✨");
        System.out.print("type \"help\" to start :)");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            System.out.print("\n >>> ");
            String line = scanner.nextLine();
            result = eval(line);
        }


    }

    public String eval(String line) {
        return switch (line) {
            case "register" -> register();
            case "login" -> login();
            case "createGame" -> createGame();
            case "joinGame" -> joinGame();
            case "observeGame" -> observeGame();
            case "logout" -> logout();
            case "quit" -> "quit";
            default -> help();
        };
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
                    createGame
                    joinGame
                    listGames
                    observeGame
                    help
                    logout
                    """;
        }
    }
}
