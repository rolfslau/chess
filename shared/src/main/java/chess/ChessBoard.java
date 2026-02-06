package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable {

    ChessPiece[][] board = new ChessPiece[8][8];
    public ChessBoard() {
        // we can represent the chess board as a 2-dimensional array
//        ChessPiece[] this.board; // should this start filled in like the start of a game?
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
//        return board[position.row][position.col]; // is this even close?
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {

        // order of pieces so that I don't have to hard code each and every one
        ChessPiece.PieceType[] order = {ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.KING,
                ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK};

        board = new ChessPiece[8][8];

        // initialize black board
        for (int i = 0; i < 8; i++) {
            board[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }
        for (int i = 0; i < 8; i++) {
            board[0][i] = new ChessPiece(ChessGame.TeamColor.WHITE, order[i]);
        }

        //initialize white board
        for (int i = 0; i < 8; i++) {
            board[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
        for (int i = 0; i < 8; i++) {
            board[7][i] = new ChessPiece(ChessGame.TeamColor.BLACK, order[i]);
        }

        // clear the rest of the board
//        for (int i = 2; i < 6; i++) {
//            for (int j = 0; j < 8; j++) {
//                board[i][j] = null;
            }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString() {
        String string = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; i < 8; i++) {
                string = string + board[i][j];
            }
        }

        return "ChessBoard{" + string;

    }

    @Override public ChessBoard clone() {
        try {
            ChessBoard clone = (ChessBoard) super.clone();
            clone.board = (ChessPiece[][]) board.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove(ChessMove move) {
        this.addPiece(move.getEndPosition(), this.getPiece(move.getStartPosition()));
        this.addPiece(move.getStartPosition(), null);
    }
}
        // what if we create an empty board first and then add in the pieces? that way i don't have to
        // go through and clear anything?


