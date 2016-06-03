package com.spyme.capture;

import com.spyme.socket.SocketServer;

import java.io.IOException;

public class Server {

    public static int PORT = 9093;

    public static void main(String[] aStrings) throws IOException {

        SocketServer server = new SocketServer(PORT);

        server.addListener((s, client) -> {
                    client.on("screen", object -> s.emitBroadcast("screen", object, client));
                    client.on("voice", object -> s.emitBroadcast("voice", object, client));
                }
        );

        server.launch();
    }
}
