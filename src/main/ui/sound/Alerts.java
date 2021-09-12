package ui.sound;

import javax.sound.sampled.*;
import java.io.File;

//Plays sounds for the appropriate action
public class Alerts {
    private File soundFile;

    //determines which sound to play based on entered parameter
    public void playSound(String sound) {
        soundFile = new File("./data/" + sound + ".wav");

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (Exception e) {
            System.out.println("Beep boop");
        }
    }
}
