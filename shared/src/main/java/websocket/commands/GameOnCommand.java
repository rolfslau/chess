package websocket.commands;

public class GameOnCommand extends UserGameCommand {

    private final boolean playing;
    private final String username;

    public GameOnCommand(UserGameCommand.CommandType commandType, String authToken, String username, int gameID, boolean playing) {
        super(commandType, authToken, gameID);
        this.playing = playing;
        this.username = username;
    }

    public boolean getPlaying() {
        return playing;
    }

    public String getUsername() { return username; }
}
