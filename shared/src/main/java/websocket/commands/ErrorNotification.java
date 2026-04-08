package websocket.commands;

public class ErrorNotification extends ServerMessage {

    private final String errorMessage;

    public ErrorNotification(ServerMessageType serverMessageType, String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
