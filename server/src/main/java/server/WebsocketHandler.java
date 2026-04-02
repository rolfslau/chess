package server;

import com.google.gson.Gson;
import io.javalin.websocket.*;
import org.eclipse.jetty.websocket.api.Session;
import websocket.commands.Notification;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

public class WebsocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    // so on connect, do i tell other people that they've joined - is that when they join the game?
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
                case CONNECT -> connect(action.getAuthToken(), ctx.session, String username, String color); // when other people connect to the game i receive a message
                case MAKE_MOVE -> makeMove(action.getAuthToken(), ctx.session); // update service/dataaccess so that I can update the game and not just the players
                case LEAVE -> leave(action.getAuthToken(), ctx.session);
                case RESIGN -> resign(action.getAuthToken(), ctx.session);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }


    private void connect(String authToken, Session session, String username, String color, int gameID) {
        connections.add(session, gameID);
        var message = String.format("%s joined game %d as %s", username, gameID, color);
        var notification = new Notification(Notification.Type.NOTIFICATION, message);
        connections.broadcast(session, gameID, notification);
    }

    private void makeMove(String authToken, Session session) {}

    private void leave(String authToken, Session session) {}

    private void resign(String authToken, Session session) {}
}

