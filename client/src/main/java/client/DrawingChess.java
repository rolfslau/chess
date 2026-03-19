package client;

import chess.ChessPiece;

import static ui.EscapeSequences.*;

public class DrawingChess {

    private final ChessPiece[][] board;

    public DrawingChess(ChessPiece[][] board) {
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
        System.out.print("     ");
        for (String h : headers) {
            System.out.printf("  %s  ", h);
        }
        System.out.print("     ");
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print("\n");
    }

    public void drawRows() {
        // for loop (to go down the rows)
        System.out.print(SET_TEXT_COLOR_WHITE);
        int turn = 0;
        for (int i = 0; i < 8; i++) {
            drawLabel(i+1);
            drawRow(board[i], turn);
            drawLabel(i+1);
            System.out.print(SET_BG_COLOR_BLACK);
            System.out.print("\n");
            turn++; // this might need editing
        }
    }

    public void drawLabel(int h) {
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(SET_TEXT_COLOR_PINK);
        System.out.printf("  %s  ", h);
    }

    public void drawRow(ChessPiece[] row, int turn) {
        // for loop (to go across the row)
        // 0 = pink; 1 = orange (bg)
        for (int i = 0; i < 8; i++) {
            if (turn % 2 == 0) { setPink(); }
            else { setOrange(); }
            turn++;
            System.out.print("  x  ");
        }
    }

    public void setOrange() {
        System.out.print(SET_BG_COLOR_ORANGE);
    }

    public void setPink() {
        System.out.print(SET_BG_COLOR_PINK);
    }

}
