package websocket.commands;

public class GameOnCommand extends UserGameCommand {

    private final String playing;
    private final String username;

    public GameOnCommand(UserGameCommand.CommandType commandType, String authToken, String username, int gameID, String playing) {
        super(commandType, authToken, gameID);
        this.playing = playing;
        this.username = username;
    }

    public String getPlaying() {
        return playing;
    }

    public String getUsername() { return username; }
}
