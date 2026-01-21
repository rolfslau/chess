package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BishopMovesCalc {

    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        if (color == ChessGame.TeamColor.WHITE) {
            for (int i = 1; i + position.getRow() < 8 && i + position.getColumn() < 8; i++) {
                ChessPosition newPos = new ChessPosition(position.getRow() + i, position.getColumn() + i);
                if (board.getPiece(newPos).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    ChessMove muv = new ChessMove(position, newPos, null);
                    moves.add(muv);
                    break;
                }
                else if (board.getPiece(newPos).getPieceType() == null) {
                    ChessMove muv = new ChessMove(position, newPos, null);
                    moves.add(muv);
                }
            }
        }
        else {
            for (int i = 1; position.getRow()-i > 0 && position.getColumn()-i > 0; i--) {
                ChessPosition newPos = new ChessPosition(position.getRow() + i, position.getColumn() + i);
                if (board.getPiece(newPos).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    ChessMove muv = new ChessMove(position, newPos, null);
                    moves.add(muv);
                    break;
                }
                else if (board.getPiece(newPos).getPieceType() == null) {
                    ChessMove muv = new ChessMove(position, newPos, null);
                    moves.add(muv);
                }
            }
        }
        return moves;
    }
}
