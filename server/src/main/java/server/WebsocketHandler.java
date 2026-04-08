package server;

import chess.ChessGame;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataaccess.MySqlUserDAO;
import dataaccess.UserDataAccess;
import exceptions.DoesNotExistException;
import io.javalin.websocket.*;
import model.Game;
import org.eclipse.jetty.websocket.api.Session;
import service.GameService;
import websocket.commands.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class WebsocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final UserDataAccess uData = new MySqlUserDAO();

    private final GameService service;
    public WebsocketHandler(GameService service) {
        this.service = service;
    }

    private final ConnectionManager connections = new ConnectionManager();

    @Override
    public void handleConnect(WsConnectContext ctx) { // ONLY USED when the user connects to a game
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand action = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (action.getCommandType()) {
                // how do I get the color ?? user game command doesn't seem set up right
                case CONNECT -> {
                    ConnectCommand connecting = new Gson().fromJson(ctx.message(), ConnectCommand.class);
                    connect(connecting.getAuthToken(), ctx.session, connecting.getGameID(),
                            connecting.getUsername(), connecting.getColor()); // when other people connect to the game i receive a message
                }
                case MAKE_MOVE -> {
                    MakeMoveCommand move = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
                    makeMove(move, ctx.session);
                } // update service/dataaccess so that I can update the game and not just the players
                case LEAVE -> {
                    LeaveCommand leave = new Gson().fromJson(ctx.message(), LeaveCommand.class);
                    leave(leave, ctx.session);
                }
                case RESIGN -> {
                    ResignCommand resign = new Gson().fromJson(ctx.message(), ResignCommand.class);
                    resign(resign, ctx.session);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }


    private void connect(String authToken, Session session, int gameID, String username, String color) throws IOException {
        try {
            connections.add(session, gameID);
            var message = String.format("%s joined game %d as %s", username, gameID, color);
            var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            ReloadNotification reload = new ReloadNotification(ServerMessage.ServerMessageType.LOAD_GAME, null, service.getGame(authToken, gameID));
            session.getRemote().sendString(new Gson().toJson(reload));
            connections.broadcast(session, gameID, notification);
        } catch (IOException | DoesNotExistException ex) {
            ErrorNotification errorNotification = new ErrorNotification(ServerMessage.ServerMessageType.ERROR, "unable to join game");
            session.getRemote().sendString(new Gson().toJson(errorNotification));
        }
    }

    private void makeMove(MakeMoveCommand action, Session session) throws IOException, RuntimeException {
        Game game;
        try {
            game = service.getGame(action.getAuthToken(), action.getGameID());
            if (Objects.equals(game.playing(), "false")) {
                throw new DoesNotExistException("game already over!!", 400);
            }
        } catch(DoesNotExistException ex) {
            ErrorNotification errorNotification = new ErrorNotification(ServerMessage.ServerMessageType.ERROR, "game is not currently active!");
            session.getRemote().sendString(new Gson().toJson(errorNotification));
            return;
//            throw new RuntimeException("no auth token");
        }
        try {
            String username = uData.getAuth(action.getAuthToken());
            MakeMoveCommand makeMoveCommand = new MakeMoveCommand(action.getCommandType(), action.getAuthToken(),
                    username, action.getGameID(), action.getMove());
            service.updateGame(makeMoveCommand);
        } catch(RuntimeException ex) {
            ErrorNotification errorNotification = new ErrorNotification(ServerMessage.ServerMessageType.ERROR, "invalid move");
            session.getRemote().sendString(new Gson().toJson(errorNotification));
            return;
//            throw new RuntimeException("invalid move!!");
        }
        var message2 = "";
        game = service.getGame(action.getAuthToken(), action.getGameID());
        if (Objects.equals(game.whiteUsername(), action.getUsername())) {
            if (game.game().isInCheckmate(ChessGame.TeamColor.BLACK) || game.game().isInStalemate(ChessGame.TeamColor.BLACK)) {
                message2 = String.format("%s is in checkmate or stalemate - game over", game.blackUsername());
                GameOnCommand command = new GameOnCommand(UserGameCommand.CommandType.MAKE_MOVE, action.getAuthToken(),
                        action.getUsername(), action.getGameID(), "false");
                service.updateGame(command);
                var notification2 = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message2);
                connections.broadcast(null, action.getGameID(), notification2);
            } else if (game.game().isInCheck(ChessGame.TeamColor.BLACK)) {
                message2 = String.format("%s is in check!", game.blackUsername());
                var notification2 = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message2);
                connections.broadcast(null, action.getGameID(), notification2);
            }
        }
        else {
                if (game.game().isInCheckmate(ChessGame.TeamColor.WHITE) || game.game().isInStalemate(ChessGame.TeamColor.WHITE)) {
                    message2 = String.format("%s is in checkmate or stalemate - game over", game.whiteUsername());
                    GameOnCommand command = new GameOnCommand(UserGameCommand.CommandType.MAKE_MOVE, action.getAuthToken(),
                            action.getUsername(), action.getGameID(), "false");
                    service.updateGame(command);
                    var notification2 = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message2);
                    connections.broadcast(null, action.getGameID(), notification2);
                }
                else if (game.game().isInCheck(ChessGame.TeamColor.WHITE)) {
                    message2 = String.format("%s is in check!", game.whiteUsername());
                    var notification2 = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message2);
                    connections.broadcast(null, action.getGameID(), notification2);
                }
        }
        String start = convertPosition(action.getMove().getStartPosition());
        String end = convertPosition(action.getMove().getEndPosition());
        var message = String.format("%s moved from %s to %s", action.getUsername(), start, end);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        var newBoard = new ReloadNotification(ServerMessage.ServerMessageType.LOAD_GAME, null,
                service.getGame(action.getAuthToken(), action.getGameID()));
        connections.broadcast(null, action.getGameID(), newBoard);
        connections.broadcast(session, action.getGameID(), notification);
    }

    private void leave(LeaveCommand action, Session session) throws IOException {
        connections.remove(action, session);
        String username = uData.getAuth(action.getAuthToken());
        Game game = service.getGame(action.getAuthToken(), action.getGameID());
        String color = "";
        if (Objects.equals(username, game.whiteUsername())) {
            color = "WHITE";
        }
        else if (Objects.equals(username, game.blackUsername())) {
            color = "BLACK";
        }
        if (!color.isEmpty()) {
            LeaveCommand command = new LeaveCommand(UserGameCommand.CommandType.LEAVE, action.getAuthToken(), username, action.getGameID(), color);
            service.updateGame(command);
        }
        // update the game somehow
        var message = String.format("%s left the game", action.getUsername());
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(session, action.getGameID(), notification);
    }


    private void resign(ResignCommand action, Session session) throws IOException {
        GameOnCommand gameOnCommand = new GameOnCommand(UserGameCommand.CommandType.RESIGN, action.getAuthToken(),
                action.getUsername(), action.getGameID(), "false");
        String username = uData.getAuth(action.getAuthToken());
        Game game = service.getGame(action.getAuthToken(), action.getGameID());
        if (!Objects.equals(username, game.whiteUsername()) && !Objects.equals(username, game.blackUsername())) {
            var message = "you are an observer and can't resign";
            var notification = new ErrorNotification(ServerMessage.ServerMessageType.ERROR, message);
            session.getRemote().sendString(new Gson().toJson(notification));
            return;
        }
        if (Objects.equals(game.playing(), "false")) {
            var message = "the game is already over";
            var notification = new ErrorNotification(ServerMessage.ServerMessageType.ERROR, message);
            session.getRemote().sendString(new Gson().toJson(notification));
            return;
        }
        service.updateGame(gameOnCommand);
        var message = String.format("%s resigned", action.getUsername());
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast(null, action.getGameID(), notification);
    }

    private final Map<Integer, String> coords = Map.of(
             1, "A",
             2, "B",
             3, "C",
             4, "D",
             5, "E",
             6, "F",
             7, "G",
             8, "H"
    );

    private String convertPosition(ChessPosition pos) {
        String col = coords.get(pos.getColumn());
        return col + pos.getRow();
    }
}

