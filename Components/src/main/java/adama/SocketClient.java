package adama;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

/**
 *
 */
public class SocketClient implements Runnable, Serializable {

    private Socket socket;
    private HashMap<String, SocketRequest> contexts;

    public SocketClient(Socket socket) {
        this.contexts = new HashMap<>();
        this.socket = socket;
    }

    public void emit(String context, Object object) {
        try {

            ObjectOutputStream stream = new ObjectOutputStream(this.socket.getOutputStream());
            stream.writeObject(
                    new SocketData(
                            context,
                            object
                    )
            );

        } catch (IOException e) {
        }
    }

    public void on(String context, SocketRequest request) {
        this.contexts.put(context, request);
    }

    synchronized public void run() {
        boolean run = this.socket.isConnected();
        while (run) {
            try {
                ObjectInputStream stream = new ObjectInputStream(this.socket.getInputStream());
                SocketData socketData = (SocketData) stream.readObject();
                this.notifyRequest(socketData);
            } catch (Exception e) {
                run = !e.getClass().equals(SocketException.class);
            }
        }
    }

    synchronized private void notifyRequest(SocketData socketData) {
        SocketRequest request = this.contexts.get(socketData.getContext());

        if (request != null) {
            request.eventRequest(socketData.getData());
        }
    }
}
