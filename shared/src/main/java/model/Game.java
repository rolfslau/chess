package model;

import chess.ChessGame;

public record Game(int gameID, String whiteUsername, String blackUsername, String gameName, String playing, ChessGame game) {
}
