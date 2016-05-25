package com.spyme.capture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Screen implements Serializable {

    private byte [] bytes;

    public Screen(BufferedImage image) throws IOException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        ImageIO.write(image, "jpg", stream);
        stream.flush();
        this.bytes = stream.toByteArray();
        stream.close();
    }

    public Screen (byte [] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Image getImage() throws IOException {
        InputStream in = new ByteArrayInputStream(this.bytes);
        return ImageIO.read(in);
    }
}
