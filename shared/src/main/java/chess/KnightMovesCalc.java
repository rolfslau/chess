package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] poses = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}};
        moves.addAll(findMove(board, position, poses));
        return moves;
    }

    public static Collection<ChessMove> findMove(ChessBoard board, ChessPosition pos, int[][] poses) {
        ArrayList<ChessMove> muvs = new ArrayList<>();
        for (int[] p : poses) {
            if (pos.getRow() + p[0] <= 8 && pos.getRow() + p[0] > 0 && pos.getColumn() + p[1] <= 8 && pos.getColumn() + p[1] > 0) {
                ChessPosition newPos = new ChessPosition(pos.getRow() + p[0], pos.getColumn() + p[1]);
                if (board.getPiece(newPos) == null || board.getPiece(newPos).getTeamColor() != board.getPiece(pos).getTeamColor()) {
                    muvs.add(new ChessMove(pos, newPos, null));
                }
            }
        }
        return muvs;
    }
}
