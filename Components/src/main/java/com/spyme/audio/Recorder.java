package com.spyme.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Recorder implements Runnable {

    private int durationPerSecond = 0;

    private List<RecorderListener> recorderListeners;

    public Recorder() {

        this.recorderListeners = new ArrayList<>();
    }

    public void setDurationPerSecond(int durationPerSecond) {
        this.durationPerSecond = durationPerSecond;
    }

    public int getDurationPerSecond() {
        return this.durationPerSecond;
    }

    public void run() {

        try {
            while (true) {

                AudioFormat format = new AudioFormat(
                        AudioFormat.Encoding.PCM_SIGNED,
                        44100 * 4,
                        16,
                        2,
                        4,
                        44100 * 4,
                        true
                );

                DataLine.Info info = new DataLine.Info(
                        TargetDataLine.class,
                        format
                );

                if (info.isFormatSupported(format)) {
                    TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
                    targetDataLine.open();
                    targetDataLine.start();
                    AudioInputStream inputStream = new AudioInputStream(targetDataLine);
                    File file = new File("tmp.wav");
                    file.deleteOnExit();

                    Thread thread = new Thread(() -> {
                        try {
                            AudioSystem.write(inputStream, AudioFileFormat.Type.WAVE, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

                    thread.start();
                    Thread.sleep(1000 * this.getDurationPerSecond());
                    targetDataLine.close();

                    byte[] bFile = new byte[(int) file.length()];

                    FileInputStream fileInputStream = new FileInputStream(file);
                    fileInputStream.read(bFile);
                    fileInputStream.close();

                    this.notifyListeners(bFile);
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyListeners(byte[] bytes) {

        for (RecorderListener listener : this.recorderListeners)
            listener.onRecord(bytes);
    }

    public void addRecorderListener(RecorderListener listener) {
        this.recorderListeners.add(listener);
    }

    public void removeListener(RecorderListener listener) {
        this.recorderListeners.remove(listener);
    }
}
