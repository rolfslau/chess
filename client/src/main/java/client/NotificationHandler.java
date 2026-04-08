package client;

import websocket.commands.ErrorNotification;
import websocket.commands.ServerMessage;
import websocket.commands.ReloadNotification;

public interface NotificationHandler {
    void notify(ServerMessage notification);
    void notifyReload(ReloadNotification reload);
    void notifyError(ErrorNotification error);
}
