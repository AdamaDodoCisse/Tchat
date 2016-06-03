package com.spyme.capture;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Voice implements Serializable {

    private byte[] bytes;

    public Voice(byte[] bytes) {

        this.bytes = bytes;
    }

    public void run() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(this.bytes));

        if (AudioSystem.getClip().isActive()) {
            AudioSystem.getClip().stop();
        }

        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    }
}
