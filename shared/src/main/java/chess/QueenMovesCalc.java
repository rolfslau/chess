package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        moves.addAll(BishopMovesCalc.getMoves(board, position));
        moves.addAll(RookMovesCalc.getMoves(board, position));
        return moves;
    }
}
