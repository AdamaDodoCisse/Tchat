package com.socket.component;

public interface SocketServerListener {

    void onConnect(SocketServer server, SocketClient client);
}
