package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor team;
    private ChessBoard board;
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // invalid if not valid for that piece or not that teams turn
        // do I check that here or in validMoves?
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if (!validMoves.contains(move)) {
            throw new InvalidMoveException("not a valid move");
        }
        else {
           // how do I actually make the move?
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        Collection<ChessMove> opMoves = new ArrayList<>();
        ChessPosition kingPos = findKing();
        ChessBoard board = getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                if (board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() != teamColor) {
                    opMoves.addAll(validMoves(pos));
                    // how do I get the board if I can't add to
                    // the class constructor?
                }
            }
        }

        for (ChessMove muv : opMoves) {
            if (muv.getEndPosition() == kingPos) { return true; }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (getTeamTurn() != teamColor) { return false; }
        if (!isInCheck(teamColor)) { return false; }
        ChessBoard board = getBoard();

        Collection<ChessMove> validMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                if (board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == teamColor) {
                    validMoves.addAll(validMoves(pos));
                    // how do I get the board if I can't add to
                    // the class constructor?
                }
            }
        }
        return validMoves.isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        ChessBoard board = getBoard();

        Collection<ChessMove> validMoves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPosition pos = new ChessPosition(i, j);
                if (board.getPiece(pos) != null && board.getPiece(pos).getTeamColor() == teamColor) {
                    validMoves.addAll(validMoves(pos));
                    // how do I get the board if I can't add to
                    // the class constructor?
                }
            }
        }
        return validMoves.isEmpty() && !isInCheck(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    public ChessPosition findKing() {
        return new ChessPosition(0, 0);
    }
}
