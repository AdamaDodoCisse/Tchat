import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by balbe on 09/04/2016.
 */
public class Screen implements Serializable {

    private byte [] bytes;

    public Screen(byte [] bytes) {
        this.bytes = bytes;
    }

    public Screen(Image image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage) image, "jpg", baos);
        baos.flush();
        this.bytes = baos.toByteArray();
        baos.close();
    }

    public Image getImage() throws IOException {
        return ImageIO.read(new ByteArrayInputStream(this.bytes));
    }

    public byte [] getBytes()
    {
        return this.bytes;
    }
}
