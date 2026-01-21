package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PawnMovesCalc {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {
        Collection<ChessMove> moves = new ArrayList<>();
        moves.addAll(diag(board, position, color));
        moves.addAll(straight(board, position, color));
        return moves;
    }

    public static Collection<ChessMove> diag(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        ArrayList<ChessMove> muvs = new ArrayList<>();
        int[][] poses;
        if (color == ChessGame.TeamColor.WHITE) { poses = new int[][] {{-1, 1}, {1, 1}}; }
        else { poses = new int[][] {{-1, -1}, {1, -1}}; }
        for (int[] p : poses) {
            if (pos.getRow() + p[0] > 0 && pos.getRow() + p[0] <=8 && pos.getColumn() + p[1] > 0 && pos.getColumn() + p[1] <= 8) {
                ChessPosition newPos = new ChessPosition(pos.getRow() + p[0], pos.getColumn() + p[1]);
                Boolean promote = false;
                if (newPos.getRow() == 1 || newPos.getRow() == 8) {
                    promote = true;
                }
                if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != color) {
                    ChessPiece.PieceType promotion;
                    if (promote) {
                        promotion = ChessPiece.PieceType.KNIGHT;
                    } else {
                        promotion = null;
                    }
                    muvs.add(new ChessMove(pos, newPos, promotion));
                }
            }
        }
        return muvs;
    }

    public static Collection<ChessMove> straight(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        ArrayList<ChessMove> muvs = new ArrayList<>();
        int[][] poses;
        if (color == ChessGame.TeamColor.WHITE) { poses = new int[][] {{1, 0}, {2, 0}}; }
        else { poses = new int[][] {{-1, 0}, {-2, 0}}; }
        for (int[] p : poses) {
            if (pos.getRow() + p[0] > 0 && pos.getRow() + p[0] <=8 && pos.getColumn() + p[1] > 0 && pos.getColumn() + p[1] <= 8) {
                ChessPosition newPos = new ChessPosition(pos.getRow() + p[0], pos.getColumn() + p[1]);

                Boolean promote = false;
                if (newPos.getRow() == 1 || newPos.getRow() == 8) {
                    promote = true;
                }
                if (board.getPiece(newPos) == null) {
                    ChessPiece.PieceType promotion;
                    if (promote) {
                        promotion = ChessPiece.PieceType.KNIGHT;
                    } else {
                        promotion = null;
                    }
                    muvs.add(new ChessMove(pos, newPos, promotion));
                }
            }
        }
    }
}
