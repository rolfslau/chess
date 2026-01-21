package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        moves.addAll(BishopMovesCalc.findMove(board, position, 0, 1));
        moves.addAll(BishopMovesCalc.findMove(board, position, 0, -1));
        moves.addAll(BishopMovesCalc.findMove(board, position, 1, 0));
        moves.addAll(BishopMovesCalc.findMove(board, position, -1, 0));
        return moves;
    }
}
