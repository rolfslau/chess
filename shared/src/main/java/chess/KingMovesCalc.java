package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] poses = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
        moves.addAll(KnightMovesCalc.findMove(board, position, poses));
        return moves;
    }
}
