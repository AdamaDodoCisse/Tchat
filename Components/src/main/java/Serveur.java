import adama.SocketServer;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by balbe on 09/04/2016.
 */
public class Serveur extends JPanel {

    private SocketServer server;

    private Image currentImage;

    private JButton screen;

    public Serveur(int width, int height, int port) {
        this.setPreferredSize(new Dimension(width, height));

        this.server = new SocketServer(port);
        this.screen = new JButton("Screen");

        this.bindEvent();
    }

    public void paint(Graphics graphics) {
        if (this.currentImage != null) {
            super.paint(graphics);
            graphics.drawImage(
                    this.currentImage,
                    0,
                    0,
                    this.getWidth(),
                    this.getHeight(),
                    null
            );
        }
    }

    public void start() {
        this.server.start();
    }

    private void bindEvent() {

        this.screen.addActionListener(e ->
                this.server
                        .getClients()
                        .stream()
                        .forEach(socketClient -> socketClient.emit("screen", true))
        );

        this.server.addClientListener(client ->
                client.on("screen", o -> {
                    try {
                        updateImage(((Screen) o).getImage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    client.emit("screen", true);
                })
        );
    }

    public void updateImage(Image image) {
        this.currentImage = image;
        repaint();
    }

    public static void main(String[] arfs) {
        JFrame frame = new JFrame("Serveur");
        JPanel panel = new JPanel();
        frame.setLayout(new BorderLayout());
        Serveur serveur = new Serveur(400, 300, 9000);
        panel.add(serveur.screen);
        frame.add(BorderLayout.NORTH, serveur);
        frame.add(BorderLayout.CENTER, panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        serveur.start();
    }
}
