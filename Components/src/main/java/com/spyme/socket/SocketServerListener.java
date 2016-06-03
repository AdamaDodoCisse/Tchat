package com.spyme.socket;

public interface SocketServerListener {

    void onConnect(SocketServer server, SocketClient client);
}
