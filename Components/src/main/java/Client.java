import adama.SocketClient;
import sun.plugin2.os.windows.Windows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;

public class Client {

    private SocketClient client;
    private Robot robot;

    public Client(String ip, int port) throws IOException, AWTException {
        this.client = new SocketClient(new Socket(ip, port));
        this.robot = new Robot();
        this.bindEvent();
    }

    public void start() {
        this.client.run();
    }

    private void bindEvent() {
        this.client.on("screen", o -> {
            try {
                Point position = MouseInfo.getPointerInfo().getLocation();

                Image image = this.robot.createScreenCapture(
                        MouseInfo
                                .getPointerInfo()
                                .getDevice()
                                .getDefaultConfiguration()
                                .getBounds()
                );

                image.getGraphics().setColor(Color.white);
                image.getGraphics().fill3DRect(
                        position.x,
                        position.y,
                        10,
                        10,
                        true
                );
                this.client.emit("screen", new Screen(image));
            } catch (Exception e) {
            }
        });
    }


    public static void main(String[] arfs) {
        try {
            Client client = new Client("127.0.0.1", 9000);
            client.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
