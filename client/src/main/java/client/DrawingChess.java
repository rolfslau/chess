package client;

import chess.*;

import java.util.Collection;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class DrawingChess {

    private final ChessBoard board;
    private final String colorOfPlayer;
    private final Collection<ChessPosition> moves;

    public DrawingChess(ChessBoard board, String colorOfPlayer, Collection<ChessPosition> moves) {
        this.board = board;
        this.colorOfPlayer = colorOfPlayer;
        this.moves = moves;
        String[] headersWhite = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] headersBlack = {"h", "g", "f", "e", "d", "c", "b", "a"};
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(SET_TEXT_COLOR_ORANGE);
        // need to check what color the player is
        if (Objects.equals(this.colorOfPlayer, "WHITE")) {
            drawHeaders(headersWhite);
            drawRowsWhite();
            System.out.print(SET_BG_COLOR_WHITE);
            System.out.print(SET_TEXT_COLOR_ORANGE);
            drawHeaders(headersWhite);
        }
        else if (Objects.equals(this.colorOfPlayer, "BLACK")) {
            drawHeaders(headersBlack);
            drawRowsBlack();
            System.out.print(SET_BG_COLOR_WHITE);
            System.out.print(SET_TEXT_COLOR_ORANGE);
            drawHeaders(headersBlack);
        }
        else { System.out.print("invalid color option provided"); }
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(RESET_BG_COLOR);
    }

    public void drawHeaders(String[] headers) {
        System.out.print("   ");
        for (String h : headers) {
            System.out.printf(" %s ", h);
        }
        System.out.print("   ");
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
    }

    public void drawRowsWhite() {
        int turn = 0;
        for (int i = 8; i > 0; i--) {
            drawingRows(i, turn);
            turn++;
        }
    }

    public void drawRowsBlack() {
        // for loop (to go down the rows)
        int turn = 0;
        for (int i = 1; i < 9; i++) {
            drawingRows(i, turn);
            turn++; // this might need editing
        }
    }

    private void drawingRows(int i, int turn) {
        drawLabel(i);
        setTextColor(i);
        if (Objects.equals(colorOfPlayer, "WHITE")) { drawRowWhite(i, turn); }
        else { drawRowBlack(i, turn); }
        drawLabel(i);
        System.out.print(RESET_BG_COLOR);
        System.out.print("\n");
    }

    private void setTextColor(int i) {
        if (i > 4) { System.out.print(SET_TEXT_COLOR_BLACK); }
        else { System.out.print(SET_TEXT_COLOR_WHITE); }
    }


    public void drawLabel(int h) {
        System.out.print(SET_BG_COLOR_WHITE);
        System.out.print(SET_TEXT_COLOR_PINK);
        System.out.printf(" %s ", h);
    }

    public void drawRowWhite(int row, int turn) {
        // for loop (to go across the row)
        // 0/even = pink; 1/odd = orange (bg)
        for (int i = 1; i < 9; i++) {
            String color = setBGColor(turn);
            ChessPosition currPos = new ChessPosition(row, i);
            if (moves.contains(currPos)) {
                switch (color) {
                case ("PINK") -> System.out.print(SET_BG_COLOR_MUSTARD);
                case ("ORANGE") -> System.out.print(SET_BG_COLOR_MUSTARD2);
                }
            }
            turn++;
            printPiece(row, i);
        }
    }

    public void drawRowBlack(int row, int turn) {
        // for loop (to go across the row)
        // 0/even = pink; 1/odd = orange (bg)
        for (int i = 8; i > 0; i--) {
            String color = setBGColor(turn);
            ChessPosition currPos = new ChessPosition(row, i);
            if (moves.contains(currPos)) {
                switch (color) {
                    case ("PINK") -> System.out.print(SET_BG_COLOR_MUSTARD);
                    case ("ORANGE") -> System.out.print(SET_BG_COLOR_MUSTARD2);
                }
            }
            turn++;
            printPiece(row, i);
        }
    }

    private String setBGColor(int turn) {
        if (turn % 2 == 1) { setPink(); return "PINK"; }
        else { setOrange(); return "ORANGE"; }
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
