package adama;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

/**
 * Socket client.
 */
public class SocketClient implements Runnable, Serializable {

    private Socket socket;
    private HashMap<String, SocketRequest> contexts;

    /**
     *
     * @param socket
     */
    public SocketClient(Socket socket) {
        this.contexts = new HashMap<>();
        this.socket = socket;
    }

    /**
     * when client send data.
     *
     * @param context emit id
     * @param object received data
     */
    public void emit(String context, Object object) {
        try {

            ObjectOutputStream stream = new ObjectOutputStream(
                    this.socket.getOutputStream()
            );
            stream.writeObject(
                    new SocketData(
                            context,
                            object
                    )
            );
        } catch (IOException e) {
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * when client receive message.
     *
     * @param context id
     * @param request
     */
    public void on(String context, SocketRequest request) {
        this.contexts.put(context, request);
    }

    /**
     *
     */
    public void run() {
        boolean run = this.socket.isConnected();

        while (run) {
            try {
                ObjectInputStream stream = new ObjectInputStream(this.socket.getInputStream());
                SocketData socketData = (SocketData) stream.readObject();
                this.notifyRequest(socketData);
            } catch (SocketException e) {
                run = false;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     *
     * @param socketData
     */
    private void notifyRequest(SocketData socketData) {
        SocketRequest request = this.contexts.get(socketData.getContext());

        if (request != null) {
            request.eventRequest(socketData.getData());
        }
    }
}
