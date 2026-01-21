package chess;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BishopMovesCalc {

    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        if (color == ChessGame.TeamColor.WHITE) {
            for (int i = 1; i + position.getRow() < 8 && position.getColumn()+ i < 8; i++) {
                ChessPosition newPos = new ChessPosition(position.getRow() + i, position.getColumn() + i);
                if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() == ChessGame.TeamColor.WHITE) { break; }
                List<Object> result = findMove(board, position, newPos);
                Boolean keepGoing = (Boolean) result.get(1);
                ChessMove muv = (ChessMove) result.get(0);
                moves.add(muv);
                if (!keepGoing) { break; }
            }
            for (int i = -1; position.getRow()-i > 0 && position.getColumn()+i < 8; i--) {
                ChessPosition newPos = new ChessPosition(position.getRow() - i, position.getColumn() - i);
                if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() == ChessGame.TeamColor.WHITE) { break; }
                List<Object> result = findMove(board, position, newPos);
                Boolean keepGoing = (Boolean) result.get(1);
                ChessMove muv = (ChessMove) result.get(0);
                moves.add(muv);
                if (!keepGoing) { break; }
            }
        }
        else {
            for (int i = 1; position.getRow()-i > 0 && position.getColumn()-i > 0; i++) {
                ChessPosition newPos = new ChessPosition(position.getRow() - i, position.getColumn() - i);
                if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() == ChessGame.TeamColor.BLACK) { break; }
                List<Object> result = findMove(board, position, newPos);
                Boolean keepGoing = (Boolean) result.get(1);
                ChessMove muv = (ChessMove) result.get(0);
                moves.add(muv);
                if (!keepGoing) { break; }
            }
            for (int i = -1; i + position.getRow() < 8 && i + position.getColumn() > 0; i--) {
                ChessPosition newPos = new ChessPosition(position.getRow() + i, position.getColumn() + i);
                if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() == ChessGame.TeamColor.BLACK) { break; }
                List<Object> result = findMove(board, position, newPos);
                Boolean keepGoing = (Boolean) result.get(1);
                ChessMove muv = (ChessMove) result.get(0);
                moves.add(muv);
                if (!keepGoing) { break; }
            }

        }
        return moves;
    }

    public static List<Object> findMove(ChessBoard board, ChessPosition pos, ChessPosition newPos) {
        if (board.getPiece(newPos) == null) {
             return List.of(new ChessMove(pos, newPos, null), true);
        }
        else {
            return List.of(new ChessMove(pos, newPos, null), false);
        }
    }
}
