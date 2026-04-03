package websocket.commands;

public class ConnectCommand extends UserGameCommand {

    String username;
    String color;

    public ConnectCommand(CommandType commandType, String authToken, Integer gameID, String username, String color) {
        super(commandType, authToken, gameID);
        this.username = username;
        this.color = color;
    }

    public String getUsername() {
        return username;
    }

    public String getColor() {
        return color;
    }
}
