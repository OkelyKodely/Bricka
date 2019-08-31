import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.Color;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class App {

    public static int WIDTH = 487;
    public static int HEIGHT = 780;

    public static JFrame frame = new JFrame();

    public static void main (String[] args) {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Keyboard keyboard = Keyboard.getInstance();
        frame.addKeyListener(keyboard);

        GamePanel panel = new GamePanel(frame);
        panel.setBackground(new Color(0, 0, 0));
        frame.add(panel);

        addSound();
    }

    public static void addSound() {
        try {
            File audioFile = new File("lib/mario.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            
            AudioFormat format = audioStream.getFormat();
            
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioStream.close();
        } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
        } catch (IOException iOException) {
        } catch (LineUnavailableException lineUnavailableException) {
        }
    }
}
