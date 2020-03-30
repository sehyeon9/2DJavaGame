package GameView;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {
    
    private Clip clip;
    
    public BackgroundMusic(String filepath) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void play() {
        if (clip == null) {
            return;
        }
        stop();
        clip.setFramePosition(0);
        clip.start();
    }
    
    public void stop() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }
    
    public void close() {
        stop();
        clip.close();
    }
    
    public Clip getClip() {
        return clip;
    }
    
}
