import com.spyme.audio.Recorder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.ByteArrayInputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        Recorder recorder = new Recorder();

        recorder.setDurationPerSecond(1);
        recorder.addRecorderListener(bytes -> {
            ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(stream);

                AudioSystem.getClip().close();

                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (Exception e) {}
        });

        Thread thread = new Thread(recorder);

        thread.start();
    }
}
