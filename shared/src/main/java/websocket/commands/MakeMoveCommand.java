package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    private final ChessMove move;
    private final String username;

    public MakeMoveCommand(CommandType commandType, String authToken, String username, Integer gameID, ChessMove move) {
        super(commandType, authToken, gameID);
        this.username = username;
        this.move = move;
    }

    public String getUsername() {
        return username;
    }

    public ChessMove getMove() { return move; }

}
