package websocket.commands;

import com.google.gson.Gson;
import model.Game;

public class ReloadNotification extends ServerMessage {

    private final String game;

    public ReloadNotification(ServerMessageType type, String message, Game game) {
        super(type, message);
        this.game = new Gson().toJson(game);
    }

    public String getGame() { return game; }
}
