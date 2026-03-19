package client;

import chess.ChessBoard;
import chess.ChessPiece;
import chess.ChessPosition;

import static ui.EscapeSequences.*;

public class DrawingChess {

    private final ChessBoard board;

    public DrawingChess(ChessBoard board) {
        this.board = board;
        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(SET_TEXT_COLOR_ORANGE);
        drawHeaders(headers);
        drawRows();
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(SET_TEXT_COLOR_ORANGE);
        drawHeaders(headers);
    }

    public void drawHeaders(String[] headers) {
        System.out.print("   ");
        for (String h : headers) {
            System.out.printf(" %s ", h);
        }
        System.out.print("   ");
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\n");
    }

    public void drawRows() {
        // for loop (to go down the rows)
        int turn = 0;
        for (int i = 1; i < 9; i++) {
            drawLabel(i);
            System.out.print(SET_TEXT_COLOR_WHITE);
            drawRow(i, turn);
            drawLabel(i);
            System.out.print(SET_BG_COLOR_BLACK);
            System.out.print("\n");
            turn++; // this might need editing
        }
    }


    public void drawLabel(String h) {
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(SET_TEXT_COLOR_PINK);
        System.out.printf(" %s ", h);
    }

    public void drawLabel(int h) {
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(SET_TEXT_COLOR_PINK);
        System.out.printf(" %s ", h);
    }

    public void drawRow(int row, int turn) {
        // for loop (to go across the row)
        // 0/even = pink; 1/odd = orange (bg)
        for (int i = 1; i < 9; i++) {
            setBGColor(turn);
            turn++;
            printPiece(row, i);
        }
    }

    private void setBGColor(int turn) {
        if (turn % 2 == 0) { setPink(); }
        else { setOrange(); }
    }

    private void printPiece(int row, int i) {
        ChessPosition pos = new ChessPosition(row, i);
        ChessPiece piece = board.getPiece(pos);
        String p = "";
        if (piece == null) { p = " "; }
        else {
            switch (piece.getPieceType()) {
                case ROOK -> p = "R";
                case KNIGHT -> p = "N";
                case BISHOP ->p = "B";
                case KING -> p = "K";
                case QUEEN -> p = "Q";
                case PAWN -> p = "P";
            }
        }
        System.out.printf(" %s ", p);
    }

    public void setOrange() {
        System.out.print(SET_BG_COLOR_ORANGE);
    }

    public void setPink() {
        System.out.print(SET_BG_COLOR_PINK);
    }

}
