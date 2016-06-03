package com.socket.component;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.net.Socket;

public class SocketServer implements Runnable{

    private ServerSocket server;

    private ArrayList<SocketClient> clients;

    private boolean asynchrone;

    private ArrayList<SocketServerListener> socketServerListeners;

    public SocketServer(int port, boolean asynchrone) throws IOException {
        this.server = new ServerSocket(port);
        this.clients = new ArrayList<>();
        this.asynchrone = asynchrone;
        this.socketServerListeners = new ArrayList<>();
    }

    public SocketServer(int port) throws IOException {
        this(port, true);
    }

    public ArrayList<SocketClient> getClients()
    {
        return this.clients;
    }

    public void emitBroadcast(String message, Object object, SocketClient client, EmitListener listener)
    {
        this.clients.forEach(socketClient -> {
            if (socketClient != client) {
                socketClient.emit(message, object, listener);
            }
        });
    }

    public void emitBroadcast(String message, Object object, SocketClient client)
    {
       this.emitBroadcast(message, object, client, null);
    }

    private void start()
    {
       while (true)
       {
           try {
               Socket client = this.server.accept();
               SocketClient cli = new SocketClient(client);
               this.clients.add(cli);
               this.notifyListeners(cli);
               cli.run();
           } catch (IOException e) {
           }
       }
    }

    public void launch()
    {
        if (this.asynchrone)
        {
            new Thread(this).start();
        }
        else
        {
            start();
        }
    }

    @Override
    public void run() {
        start();
    }

    public void addListener(SocketServerListener listener)
    {
        if (this.socketServerListeners != null) {
            this.socketServerListeners.add(listener);
        }
    }

    public void removeListener(SocketServerListener listener)
    {
        if (this.socketServerListeners != null) {
            this.socketServerListeners.remove(listener);
        }
    }

    public void notifyListeners(SocketClient socketClient)
    {
        if ( this.socketServerListeners != null) {
            for (SocketServerListener listener : this.socketServerListeners) {
                listener.onConnect(this, socketClient);
            }
        }
    }
}
