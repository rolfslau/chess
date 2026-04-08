package websocket.commands;

public class ErrorNotification extends ServerMessage {

    public ErrorNotification(ServerMessageType type, String message) {
        super(type, message);
    }
}
