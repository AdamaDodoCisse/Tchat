package adama;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 */
public class SocketServer {

    private ServerSocket socket;
    private ArrayList<SocketClient> clients;
    private ArrayList<SocketClientListener> listeners;

    public SocketServer(int port) throws RuntimeException {
        this.clients = new ArrayList<>();
        this.listeners = new ArrayList<>();

        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addClientListener(SocketClientListener listener) {
        this.listeners.add(listener);
    }

    public void notifyAllListener(SocketClient client) {
        for (SocketClientListener listener : this.listeners)
            listener.onConnect(client);
    }

    public ArrayList<SocketClient> getClients() {
        return this.clients;
    }

    public void start() {
        while (true) {
            try {
                SocketClient client = new SocketClient(this.socket.accept());
                this.clients.add(client);
                this.notifyAllListener(client);
                new Thread(client).start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
