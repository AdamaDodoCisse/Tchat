package com.spyme.capture;

import com.spyme.socket.SocketClient;
import com.spyme.audio.Recorder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Client extends JPanel {

    private Screen screen;

    public Client() {
        this.setPreferredSize(
                new Dimension(
                        600,
                        400
                )
        );
    }

    public void update() {
        this.repaint();
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void paint(Graphics graphics) {

        if (this.screen != null) {
            super.paint(graphics);

            try {
                graphics.drawImage(
                        this.screen.getImage(),
                        0,
                        0,
                        (int) this.getPreferredSize().getWidth(),
                        (int) this.getPreferredSize().getHeight(),
                        null
                );
            } catch (IOException e) {
            }
        }
    }

    public static void main(String[] aStrings) throws Exception {

        SocketClient client = new SocketClient("127.0.0.1", Server.PORT);
        Recorder recorder = new Recorder();

        JFrame frame = new JFrame();
        Client panelClient = new Client();
        frame.add(panelClient);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        client.on("screen", screen -> {
            panelClient.setScreen((Screen) screen);
            try {
                client.emit("screen", getScreen());
                panelClient.update();
            } catch (Exception e) {
            }
        });

        recorder.setDurationPerSecond(1);

        recorder.addRecorderListener(bytes -> client.emit("voice", new Voice(bytes)));

        client.emit("screen", getScreen());

        client.on("voice", object -> {
            try {
                ((Voice) object).run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        client.run();
      //  recorder.run();
    }

    public static Screen getScreen() throws AWTException, IOException {
        return new Screen(
                (new Robot()).createScreenCapture(
                        MouseInfo
                                .getPointerInfo()
                                .getDevice()
                                .getDefaultConfiguration()
                                .getBounds()
                )
        );
    }
}
