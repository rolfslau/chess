package chess;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class BishopMovesCalc {

    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position) {
        Collection<ChessMove> moves = new ArrayList<>();
        moves.addAll(findMove(board, position, 1, 1));
        moves.addAll(findMove(board, position, 1, -1));
        moves.addAll(findMove(board, position, -1, -1));
        moves.addAll(findMove(board, position, -1, 1));
        return moves;
    }

    public static Collection<ChessMove> findMove(ChessBoard board, ChessPosition pos, int x, int y) {
        int i = 1;
        Collection<ChessMove> muvs = new ArrayList<>();
        while (pos.getRow() + (x*i) <= 8 && pos.getRow() + (x*i) > 0 && pos.getColumn() + (y*i) > 0 && pos.getColumn() + (y*i) <= 8) {
            ChessPosition newPos = new ChessPosition(pos.getRow() + (x*i), pos.getColumn() + (y*i));
            if (board.getPiece(newPos) == null) { muvs.add(new ChessMove(pos, newPos, null)); }
            else if (board.getPiece(newPos).getTeamColor() == board.getPiece(pos).getTeamColor()) { break; }
            else { muvs.add(new ChessMove(pos, newPos, null)); break; }
            i++;
        }
        return muvs;
    }
}
