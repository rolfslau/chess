package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PieceMovesCalc {
    private ChessBoard board;
    private final ChessPosition pos;
    private final ChessGame.TeamColor color;

    public PieceMovesCalc(ChessBoard board, ChessPosition pos, ChessGame.TeamColor color) {
        this.board = board;
        this.pos = pos;
        this.color = color;
    }

    public Collection<ChessMove> findMoveRB(int[][] poses) {
        Collection<ChessMove> muvs = new ArrayList<>();
        for (int[] p : poses) {
            int x = 1;
            while (pos.getRow() + (x*p[0]) > 0 && pos.getRow() + (x*p[0]) <= 8 && pos.getColumn() + (x*p[1]) > 0 && pos.getColumn() + (x*p[1]) <= 8) {
                ChessPosition newPos = new ChessPosition(pos.getRow() + (x*p[0]), pos.getColumn() + (x*p[1]));
                if (board.getPiece(newPos) == null) {muvs.add(new ChessMove(pos, newPos, null)); }
                else if (board.getPiece(newPos).getTeamColor() == color ) { break; }
                else { muvs.add(new ChessMove(pos, newPos, null)); break; }
                x++;
            }
        }
        return muvs;
    }

    public Collection<ChessMove> findMoveKK(int[][] poses) {
        Collection<ChessMove> muvs = new ArrayList<>();
        for (int[] p : poses) {
            if (pos.getRow() + p[0] > 0 && pos.getRow() + p[0] <= 8 && pos.getColumn() + p[1] > 0 && pos.getColumn() + p[1] <= 8) {
                ChessPosition newPos = new ChessPosition(pos.getRow() + p[0], pos.getColumn() + p[1]);
                if (board.getPiece(newPos) == null) {
                    muvs.add(new ChessMove(pos, newPos, null));
                } else if (board.getPiece(newPos).getTeamColor() != color) {
                    muvs.add(new ChessMove(pos, newPos, null));
                }
            }
        }
        return muvs;
    }

    public Collection<ChessMove> findMoveQ() {
        Collection<ChessMove> muvs = new ArrayList<>();
        int[][] poses = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        muvs.addAll(findMoveRB(poses));
        return muvs;
    }

    public Collection<ChessMove> findMovePawn() {
        Collection<ChessMove> muvs = new ArrayList<>();
        muvs.addAll(straight());
        muvs.addAll(diag());
        return muvs;
    }

    public Collection<ChessMove> straight() {
        Collection<ChessMove> muvs = new ArrayList<>();
        switch (color) {
            case WHITE:
                // first move
                if (firstMove()) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() +1, pos.getColumn());
                    ChessPosition newPos2 = new ChessPosition(pos.getRow() + 2, pos.getColumn());
                    muvs.add(new ChessMove(pos, newPos, null));
                    muvs.add(new ChessMove(pos, newPos2, null));
                }

                // promotion
                else if (promote()) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() +1, pos.getColumn());
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.ROOK));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.KNIGHT));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.QUEEN));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.BISHOP));
                }

                // regular move
                else {
                    ChessPosition newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
                    if (board.getPiece(newPos) == null) {muvs.add(new ChessMove(pos, newPos, null));}
                }

                return muvs;

            case BLACK:
                // first move
                if (firstMove()) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
                    ChessPosition newPos2 = new ChessPosition(pos.getRow() - 2, pos.getColumn());
                    muvs.add(new ChessMove(pos, newPos, null));
                    muvs.add(new ChessMove(pos, newPos2, null));
                }

                // promotion
                else if (promote()) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.ROOK));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.KNIGHT));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.QUEEN));
                    muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.BISHOP));
                }

                // regular move
                else {
                    ChessPosition newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
                    if (board.getPiece(newPos) == null) {muvs.add(new ChessMove(pos, newPos, null));}
                }

        }
        return muvs;
    }

    public Collection<ChessMove> diag() {
        Collection<ChessMove> muvs = new ArrayList<>();
        switch (color) {
            case WHITE:
                // promotion
                ChessPosition newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn() + 1);
                ChessPosition newPos2 = new ChessPosition(pos.getRow() + 1, pos.getColumn() - 1);
                if (newPos.getRow() == 8) {
                    if (inBounds(newPos) && board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != color) {
                        muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.ROOK));
                        muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.KNIGHT));
                        muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.QUEEN));
                        muvs.add(new ChessMove(pos, newPos, ChessPiece.PieceType.BISHOP));
                    }
                    if (inBounds(newPos2) && board.getPiece(newPos2) != null && board.getPiece(newPos2).getTeamColor() != color) {
                        muvs.add(new ChessMove(pos, newPos2, ChessPiece.PieceType.ROOK));
                        muvs.add(new ChessMove(pos, newPos2, ChessPiece.PieceType.KNIGHT));
                        muvs.add(new ChessMove(pos, newPos2, ChessPiece.PieceType.QUEEN));
                        muvs.add(new ChessMove(pos, newPos2, ChessPiece.PieceType.BISHOP));
                    }
                }
                // regular move
                else {
                    if (inBounds(newPos) && board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != color) { muvs.add(new ChessMove(pos, newPos, null)); }
                    if (inBounds(newPos2) && board.getPiece(newPos2) != null && board.getPiece(newPos2).getTeamColor() != color) { muvs.add(new ChessMove(pos, newPos2, null)); }
                }
                return muvs;

            case BLACK:
                // promotion
                ChessPosition newPos3 = new ChessPosition(pos.getRow() - 1, pos.getColumn() - 1);
                ChessPosition newPos4 = new ChessPosition(pos.getRow() - 1, pos.getColumn() + 1);
                if (newPos3.getRow() == 1) {
                    if (inBounds(newPos3) && board.getPiece(newPos3) != null && board.getPiece(newPos3).getTeamColor() != color) {
                        muvs.add(new ChessMove(pos, newPos3, ChessPiece.PieceType.ROOK));
                        muvs.add(new ChessMove(pos, newPos3, ChessPiece.PieceType.KNIGHT));
                        muvs.add(new ChessMove(pos, newPos3, ChessPiece.PieceType.QUEEN));
                        muvs.add(new ChessMove(pos, newPos3, ChessPiece.PieceType.BISHOP));
                    }
                    if (inBounds(newPos4) && board.getPiece(newPos4) != null && board.getPiece(newPos4).getTeamColor() != color) {
                        muvs.add(new ChessMove(pos, newPos4, ChessPiece.PieceType.ROOK));
                        muvs.add(new ChessMove(pos, newPos4, ChessPiece.PieceType.KNIGHT));
                        muvs.add(new ChessMove(pos, newPos4, ChessPiece.PieceType.QUEEN));
                        muvs.add(new ChessMove(pos, newPos4, ChessPiece.PieceType.BISHOP));
                    }
                }
                // regular move
                else {
                    if (inBounds(newPos3) && board.getPiece(newPos3) != null && board.getPiece(newPos3).getTeamColor() != color) { muvs.add(new ChessMove(pos, newPos3, null)); }
                    if (inBounds(newPos4) && board.getPiece(newPos4) != null && board.getPiece(newPos4).getTeamColor() != color) { muvs.add(new ChessMove(pos, newPos4, null)); }
                }
        }
        return muvs;
    }

    public Boolean promote() {
        switch (color) {
            case WHITE:
                ChessPosition newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
                if (pos.getRow() == 7 && board.getPiece(newPos) == null) { return true; }
                else { return false; }
            case BLACK:
                ChessPosition newPos2 = new ChessPosition(pos.getRow() - 1, pos.getColumn());
                if (pos.getRow() == 2 && board.getPiece(newPos2) == null) { return true; }
                else { return false; }
        }
        return false;
    }

    public Boolean inBounds(ChessPosition pos) {
        return pos.getRow() > 0 && pos.getRow() <=8 && pos.getColumn() > 0 && pos.getColumn() <= 8;
    }

    public Boolean firstMove() {
        switch (color) {
            case WHITE:
                if (pos.getRow() == 2) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() + 1, pos.getColumn());
                    ChessPosition newPos2 = new ChessPosition(pos.getRow() + 2, pos.getColumn());
                    return board.getPiece(newPos) == null && board.getPiece(newPos2) == null;
                }
                return false;
            case BLACK:
                if (pos.getRow() == 7) {
                    ChessPosition newPos = new ChessPosition(pos.getRow() - 1, pos.getColumn());
                    ChessPosition newPos2 = new ChessPosition(pos.getRow() - 2, pos.getColumn());
                    return board.getPiece(newPos) == null && board.getPiece(newPos2) == null;
                }
        }
        return false;
    }

}
