package controller;

import adama.SocketClient;
import adama.SocketRequest;
import entity.Computer;

import javax.swing.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 */
public class ClientController {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket();
        socket.connect(
                new InetSocketAddress(
                        "127.0.0.1",
                        9000
                )
        );

        SocketClient client = new SocketClient(socket);

        client.on(
                "mouse.move",
                new SocketRequest() {
                    @Override
                    synchronized public void eventRequest(Object position) {
                        System.out.println(position);
                    }
                }
        );

        client.on(
                "message.box",
                new SocketRequest() {
                    @Override
                    synchronized public void eventRequest(Object message) {
                        JOptionPane.showMessageDialog(
                                null,
                                message
                        );
                    }
                }
        );

        client.emit(
                "computer.information",
                new Computer()
        );

        client.run();
    }
}
