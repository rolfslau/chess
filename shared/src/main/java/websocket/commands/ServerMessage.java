package websocket.commands;

import com.google.gson.Gson;

public class ServerMessage {

    private final ServerMessageType serverMessageType;
    private final String message;

    public ServerMessage(ServerMessageType serverMessageType, String message) {
        this.serverMessageType = serverMessageType;
        this.message = message;
    }

    public ServerMessage(ServerMessageType serverMessageType) {
        this.serverMessageType = serverMessageType;
        this.message = null;
    }

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public ServerMessageType getServerMessageType() { return serverMessageType; }

    public String getMessage() { return message; }
}
