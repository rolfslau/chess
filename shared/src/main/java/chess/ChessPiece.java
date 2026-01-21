package chess;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */

// don't make a class for each kind of piece -- problems when you need to add it to the database

public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */

    // dr robins vid tells us the better way to do this without subclasses
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        // look up a switch statement
        // make an instance of the class first, and then use that instance for the calculations
        switch (piece.getPieceType()) {
            case PAWN:
                return PawnMovesCalc.getMoves(board, myPosition, piece.pieceColor);
            case ROOK:
                return RookMovesCalc.getMoves(board, myPosition, piece.pieceColor);
            case KNIGHT:
                return KnightMovesCalc.getMoves(board, myPosition, piece.pieceColor);
            case KING:
                return KingMovesCalc.getMoves(board, myPosition, piece.pieceColor);
            case QUEEN:
                return QueenMovesCalc.getMoves(board, myPosition, piece.pieceColor);
            case BISHOP:
                return BishopMovesCalc.getMoves(board, myPosition, piece.pieceColor);
            default:
                return List.of();
        }
//        if (piece.getPieceType() == PieceType.BISHOP) {
//            return list.of(new ChessMove(new ChessPosition()));
//        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }
}
