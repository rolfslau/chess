package client;

import websocket.commands.Notification;

public interface NotificationHandler {
    void notify(Notification notification);
}
