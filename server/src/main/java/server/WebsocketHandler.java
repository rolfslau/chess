package server;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.UserDataAccess;
import exceptions.DoesNotExistException;
import io.javalin.websocket.*;
import model.Game;
import org.eclipse.jetty.websocket.api.Session;
import service.GameService;
import service.UserService;
import websocket.commands.*;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Objects;

public class WebsocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

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
                    connect(connecting.getAuthToken(), ctx.session, connecting.getGameID(), connecting.getUsername(), connecting.getColor()); // when other people connect to the game i receive a message
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


    // I don't understand how any information gets passed and is able to be used
    // make sub classes of user game command
    // should I make another record class where one of the parts is the user game command ???
    private void connect(String authToken, Session session, int gameID, String username, String color) throws IOException {
        connections.add(session, gameID);
        // how do I get the username here if I only have the auth token ?
        var message = String.format("%s joined game %d as %s", username, gameID, color);
        var notification = new Notification(Notification.Type.NOTIFICATION, message);
        connections.broadcast(session, gameID, notification);
    }

    private void makeMove(MakeMoveCommand action, Session session) throws IOException, DoesNotExistException {
        Game game = service.getGame(action.getAuthToken(), action.getGameID());
        if (Objects.equals(game.playing(), "false")) {
            throw new DoesNotExistException("game already over!!", 400);
        }
        service.updateGame(action);
        var message2 = "";
        game = service.getGame(action.getAuthToken(), action.getGameID());
        if (Objects.equals(game.whiteUsername(), action.getUsername())) {
                if (game.game().isInCheckmate(ChessGame.TeamColor.BLACK) || game.game().isInStalemate(ChessGame.TeamColor.BLACK)) {
                    message2 = String.format("%s is in checkmate or stalemate - game over", game.blackUsername());
                    GameOnCommand command = new GameOnCommand(UserGameCommand.CommandType.MAKE_MOVE, action.getAuthToken(), action.getUsername(), action.getGameID(), "false");
                    service.updateGame(command);
                }
                else if (game.game().isInCheck(ChessGame.TeamColor.BLACK)) {
                    message2 = String.format("%s is in check!", game.blackUsername());
                }
        else {
                if (game.game().isInCheckmate(ChessGame.TeamColor.WHITE) || game.game().isInStalemate(ChessGame.TeamColor.WHITE)) {
                    message2 = String.format("%s is in checkmate or stalemate - game over", game.whiteUsername());
                    GameOnCommand command = new GameOnCommand(UserGameCommand.CommandType.MAKE_MOVE, action.getAuthToken(), action.getUsername(), action.getGameID(), "false");
                    service.updateGame(command);
                }
                else if (game.game().isInCheck(ChessGame.TeamColor.WHITE)) {
                    message2 = String.format("%s is in check!", game.whiteUsername());
                }
            }
        }
        var message = String.format("%s moved from %s to %s", action.getUsername(), action.getMove().getStartPosition(), action.getMove().getEndPosition());
        var notification = new Notification(Notification.Type.NOTIFICATION, message);
        var notification2 = new Notification(Notification.Type.NOTIFICATION, message2);
        connections.broadcast(session, action.getGameID(), notification);
        connections.broadcast(null, action.getGameID(), notification2);
    }

    private void leave(LeaveCommand action, Session session) throws IOException {
        connections.remove(action, session);
        service.updateGame(action);
        // update the game somehow
        var message = String.format("%s left the game", action.getUsername());
        var notification = new Notification(Notification.Type.NOTIFICATION, message);
        connections.broadcast(session, action.getGameID(), notification);
    }


    private void resign(ResignCommand action, Session session) throws IOException {
        GameOnCommand gameOnCommand = new GameOnCommand(UserGameCommand.CommandType.RESIGN, action.getAuthToken(), action.getUsername(), action.getGameID(), "false");
        service.updateGame(gameOnCommand);
        var message = String.format("%s resigned", action.getUsername());
        var notification = new Notification(Notification.Type.NOTIFICATION, message);
        connections.broadcast(session, action.getGameID(), notification);
    }


    private void resign(String authToken, Session session) {}
}

