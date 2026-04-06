package websocket.commands;

public class GameOnCommand extends UserGameCommand {

    private final boolean playing;

    public GameOnCommand(UserGameCommand.CommandType commandType, String authToken, int gameID, boolean playing) {
        super(commandType, authToken, gameID);
        this.playing = playing;
    }

    public boolean getPlaying() {
        return playing;
    }
}
