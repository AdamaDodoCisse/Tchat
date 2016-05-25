package com.socket.component;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class SocketClient {

    private HashMap<String, SocketRequest> requests;

    private Socket socket;

    public SocketClient(Socket socket) {
        this.setSocket(socket);
        this.requests = new HashMap<>();
    }

    public SocketClient(String ip, int port) throws IOException {
        this(new Socket(ip, port));
    }

    public void emit(String message, Object o) {
        this.emit(message, o, new EmitListener() {
            @Override
            public void onSuccess(Object object) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    public void emit(String message, Object object, EmitListener listener) {
        if (this.getSocket() != null && this.getSocket().isConnected() && !this.getSocket().isClosed()) {
            new Thread(() -> {
                try {
                    ObjectOutputStream o = new ObjectOutputStream(this.getSocket().getOutputStream());
                    o.writeObject(new SocketInformation(message, object));
                    if (listener != null) {
                        listener.onSuccess(object);
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }).start();
        } else if (listener != null) {
            listener.onError(new RuntimeException("Socket is not available."));
        }
    }

    public void on(String message, SocketRequest request) {
        this.requests.put(message, request);
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        Thread thread = (new Thread(() -> {
            while (this.socket != null && !this.socket.isClosed() && this.socket.isConnected()) {
                try {
                    ObjectInputStream in = new ObjectInputStream(
                            getSocket().getInputStream()
                    );

                    SocketInformation information = (SocketInformation) in.readObject();

                    SocketRequest request = requests.get(information.getProtocol());

                    if (request != null) {
                        request.onRequest(information.getData());
                    }
                } catch (Exception e) {
                    //System.err.println(e.getMessage());
                }
            }

        }));

        thread.start();
    }
}
