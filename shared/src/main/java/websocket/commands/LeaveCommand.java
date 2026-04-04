package websocket.commands;

public class LeaveCommand extends UserGameCommand {

    private final String color;
    private final String username;

    public LeaveCommand(CommandType commandType, String authToken, String username, Integer gameID, String color) {
        super(commandType, authToken, gameID);
        this.color = color;
        this.username = username;
    }

    public String getColor() {
        return color;
    }

    public String getUsername() { return username; }
}
