package client;

import chess.ChessPiece;

import static ui.EscapeSequences.*;

public class DrawingChess {

    ChessPiece[][] board;

    public DrawingChess(ChessPiece[][] board) {
        this.board = board;
        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        System.out.print(SET_BG_COLOR_WHITE);
            System.out.print(SET_TEXT_COLOR_ORANGE);
        drawHeaders(headers);
        drawRows();
    }

    public void drawHeaders(String[] headers) {
        for (String h : headers) {
            System.out.printf("  %s  ", h);
        }
    }

    public void drawRows() {
        // for loop (to go down the rows)
        int turn = 0;
        for (int i = 1; i < 9; i++) {
            drawLabel(i);
            drawRow(board[i], turn);
            drawLabel(i);
            System.out.print(SET_BG_COLOR_BLACK);
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
        for (int i = 1; i < 9; i++) {
            if (turn % 2 == 0) { setPink(); }
            else { setOrange(); }
            turn++;

        }
    }

}
