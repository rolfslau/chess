package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;

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
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        switch (board.getPiece(myPosition).getPieceType()) {
            case PAWN:
                PawnMovesCalc pawn = new PawnMovesCalc(board, myPosition, board.getPiece(myPosition).getTeamColor());
                moves.addAll(pawn.findMovePawn());
                return moves;
            case ROOK:
                RookMovesCalc rook = new RookMovesCalc(board, myPosition, board.getPiece(myPosition).getTeamColor());
                int[][] poses = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
                moves.addAll(rook.findMoveRB(poses));
                return moves;
            case BISHOP:
                BishopMovesCalc bish = new BishopMovesCalc(board, myPosition, board.getPiece(myPosition).getTeamColor());
                int[][] poses2 = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                moves.addAll(bish.findMoveRB(poses2));
                return moves;
            case KNIGHT:
                KnightMovesCalc knight = new KnightMovesCalc(board, myPosition, board.getPiece(myPosition).getTeamColor());
                int[][] poses3 = {{1, 2}, {-1, 2}, {1, -2}, {-1, -2}, {2, 1}, {-2, 1}, {2, -1}, {-2, -1}};
                moves.addAll(knight.findMoveKK(poses3));
                return moves;
            case KING:
                KingMovesCalc king = new KingMovesCalc(board, myPosition, board.getPiece(myPosition).getTeamColor());
                int[][] poses4 = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
                moves.addAll(king.findMoveKK(poses4));
                return moves;
            case QUEEN:
                QueenMovesCalc queen = new QueenMovesCalc(board, myPosition, board.getPiece(myPosition).getTeamColor());
                moves.addAll(queen.findMoveQ());
                return moves;
        }
        return moves;
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
