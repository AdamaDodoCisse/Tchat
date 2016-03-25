package controller;

import adama.SocketClient;
import adama.SocketRequest;
import entity.Computer;

import javax.swing.*;
import java.awt.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 */
public class ClientController {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket();
        Robot robot = new Robot();
        socket.connect(
                new InetSocketAddress(
                        "172.16.16.31",
                        9000
                )
        );

        SocketClient client = new SocketClient(socket);

        client.on(
                "mouse.move",
                position -> {
                    if (position instanceof Point) {
                        robot.mouseMove(((Point) position).x, ((Point) position).y);
                    }
                    System.out.println(position);
                }
        );

        client.on(
                "message.box",
                message -> {
                    String s = JOptionPane.showInputDialog(
                            null,
                            message
                    );
                    client.emit("message.box", s);
                }
        );

        client.emit(
                "computer.information",
                new Computer()
        );

        client.run();
    }
}
