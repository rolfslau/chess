package service;

import chess.ChessGame;
import chess.ChessMove;
import dataaccess.GameDataAccess;
import dataaccess.UserDataAccess;
import exceptions.ResponseException;
import model.Game;
import exceptions.AlreadyExistsException;
import exceptions.DoesNotExistException;
import websocket.commands.GameOnCommand;
import websocket.commands.LeaveCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import java.util.Collection;
import java.util.Objects;

public class GameService {

    public final GameDataAccess dataAccess;
    public final UserDataAccess uData;

    public GameService(GameDataAccess dataAccess, UserDataAccess uData) {
        this.dataAccess = dataAccess;
        this.uData = uData;
    }

    public Collection<Game> listGames(String auth) throws DoesNotExistException {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        return dataAccess.listGames();
    }

    public Game getGame(String auth, int gameID) {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        if (dataAccess.getGame(gameID) == null) {
            throw new DoesNotExistException("Error: no game by that id", 400);
        }
        return dataAccess.getGame(gameID);
    }

    public int newGame(String auth, String gameName) throws DoesNotExistException {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        if (gameName == null) {
            throw new DoesNotExistException("Error: invalid game name", 400);
        }
        return dataAccess.newGame(gameName);
    }

    public void joinGame(String auth, String color, int gameID) throws DoesNotExistException, AlreadyExistsException {
        if (uData.getAuth(auth) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        String user = uData.getAuth(auth);
        Game game = dataAccess.getGame(gameID);
        if (game == null) {
            throw new DoesNotExistException("Error: no game by that id", 400);
        }
        if (!Objects.equals(color, "WHITE") && !Objects.equals(color, "BLACK")) {
            throw new DoesNotExistException("Error: not a valid color", 400);
        }

        if (colorTaken(game, color)) {
            throw new AlreadyExistsException("Error: color already taken", 403);
        }

        dataAccess.joinGame(user, color, gameID);
    }

    public void updateGame(MakeMoveCommand command) {
        if (uData.getAuth(command.getAuthToken()) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        String user = uData.getAuth(command.getAuthToken());
        Game game = dataAccess.getGame(command.getGameID());
        if (game == null) {
            throw new DoesNotExistException("Error: no game by that id", 400);
        }
        ChessGame.TeamColor curr;
        if (Objects.equals(command.getUsername(), game.whiteUsername())) { curr = ChessGame.TeamColor.WHITE; }
        else { curr = ChessGame.TeamColor.BLACK; }
        if (game.game().getTeamTurn() != curr) {
            throw new ResponseException(ResponseException.Code.ClientError, "not that player's turn!!");
        }
        Collection<ChessMove> moves = game.game().validMoves(command.getMove().getStartPosition());
        if (!moves.contains(command.getMove())) {
            throw new ResponseException(ResponseException.Code.ClientError, "not a legal move!!");
        }
        dataAccess.updateGame(command);
    }

    public void updateGame(LeaveCommand command) {
        if (uData.getAuth(command.getAuthToken()) == null) {
            throw new DoesNotExistException("Error: not authorized", 401);
        }
        String user = uData.getAuth(command.getAuthToken());
        Game game = dataAccess.getGame(command.getGameID());
        if (game == null) {
            throw new DoesNotExistException("Error: no game by that id", 400);
        }
        dataAccess.updateGame(command);
    }

    public void updateGame(GameOnCommand command) {
//        Game game = dataAccess.getGame(command.getGameID());
        dataAccess.updateGame(command);
    }

    private Boolean colorTaken(Game game, String color) {
        if (!Objects.equals(game.whiteUsername(), null) && Objects.equals(color, "WHITE")) {
            return true;
        }
        else { return !Objects.equals(game.blackUsername(), null) && Objects.equals(color, "BLACK"); }
    }

    public void clearApp() {
        uData.clearApp();
        dataAccess.clearApp();
    }

}
