package server;

import com.google.gson.Gson;
import dataaccess.UserDataAccess;
import io.javalin.websocket.*;
import org.eclipse.jetty.websocket.api.Session;
import service.GameService;
import service.UserService;
import websocket.commands.*;
import websocket.messages.ServerMessage;

import java.io.IOException;

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
                case RESIGN -> resign(action.getAuthToken(), ctx.session);
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

    private void makeMove(MakeMoveCommand action, Session session) throws IOException {
        service.updateGame(action);
        var message = String.format("%s moved from %s to %s", action.getUsername(), action.getMove().getStartPosition(), action.getMove().getEndPosition());
        var notification = new Notification(Notification.Type.NOTIFICATION, message);
        connections.broadcast(session, action.getGameID(), notification);
    }

    private void leave(LeaveCommand action, Session session) throws IOException {
        connections.remove(action, session);
        service.updateGame(action);
        // update the game somehow
        var message = String.format("%s left the game", action.getUsername());
        var notification = new Notification(Notification.Type.NOTIFICATION, message);
        connections.broadcast(session, action.getGameID(), notification);
    }

    private void resign(String authToken, Session session) {}
}

