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

        switch (color) {

            case WHITE:
                // initial move
                if (FirstMove(board, pos, color)) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
                    ChessPosition newPos2 = new ChessPosition(pos.getRow() + 2, pos.getColumn());
                    muvs.add(new ChessMove(pos, newPos, null));
                    muvs.add(new ChessMove(pos, newPos2, null));
                }

                // promotion move
                else if (Promote(board, pos, color)) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.KNIGHT));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.QUEEN));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.ROOK));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.BISHOP));
            }

                // regular move
                else {
                    ChessPosition newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
                    if (inBounds(newPos) && notBlocked(board, newPos, color)) {
                        muvs.add(new ChessMove(pos, newPos, null));
                    }
                }

            case BLACK:
                // initial move
                if (FirstMove(board, pos, color)) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
                    ChessPosition newPos2 = new ChessPosition(pos.getRow() - 2, pos.getColumn());
                    muvs.add(new ChessMove(pos, newPos, null));
                    muvs.add(new ChessMove(pos, newPos2, null));
                }

                // promotion move
                else if (Promote(board, pos, color)) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.KNIGHT));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.QUEEN));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.ROOK));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.BISHOP));
                }

                // regular move
                else {
                    ChessPosition newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
                    if (inBounds(newPos) && notBlocked(board, newPos, color)) {
                        muvs.add(new ChessMove(pos, newPos, null));
                    }
                }



        }
        // initial move
        if (pos.getRow() == 1 && color == ChessGame.TeamColor.WHITE) {}
        else if (pos.getRow() == 8 && color == ChessGame.TeamColor.BLACK) {}

        // regular move
        else if {}

        return muvs;
    }

    public static Boolean inBounds(ChessPosition pos) {
        return pos.getRow() <=8 && pos.getRow() > 0 && pos.getColumn() <=8 && pos.getColumn() > 0;
    }

    public static Boolean notBlocked(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        return board.getPiece(pos) == null;
    }

    public static Boolean FirstMove(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        ChessPosition newPos;
        ChessPosition newPos2;
        if (pos.getRow() == 2 && color == ChessGame.TeamColor.WHITE) {
            newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
            newPos2 = new ChessPosition(pos.getRow() + 2, pos.getColumn());
        }
        else if (pos.getRow() == 7 && color == ChessGame.TeamColor.BLACK) {
            newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
            newPos2 = new ChessPosition(pos.getRow() - 2, pos.getColumn());
        }
        else { return false; }

        return inBounds(newPos) && inBounds(newPos2) && notBlocked(board, newPos, color) && notBlocked(board, newPos2, color);
    }

    public static Boolean Promote(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        ChessPosition newPos;
        if (pos.getRow() == 2 && color == ChessGame.TeamColor.BLACK) {
            newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
        }
        else if (pos.getRow() == 7 && color == ChessGame.TeamColor.WHITE) {
            newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
        }
        else { return false; }

        return notBlocked(board, newPos, color);
    }
}
