import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    public boolean start;

    private Game game;
    
    private JFrame frame;

    public GamePanel (JFrame frame) {
        this.frame = frame;
        
        game = new Game();

        new Thread(this).start();
    }

    public void update () {
        game.update();
        repaint();
    }

    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        try {
            ArrayList<Render> renders = game.getRenders();
            if(renders != null)
            for (Render r : renders) {
                if(r != null)
                g.drawImage(r.image, r.x, r.y, null);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run () {
        boolean startOfGame = true;
        try {
            while (true) {
                if(startOfGame)
                    game.initBricks();
                if(startOfGame)
                    startOfGame = false;
                update();
                this.start = game.startAgain;
                if(this.start)
                    break;
                frame.setTitle("Score : " + game.point + "");
                Thread.sleep(28);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.start = false;
        game.startAgain = false;
        game.setStartOver(false);
        Thread t = new Thread() {
            public void run() {
                Graphics g = getGraphics();
                g.setColor(Color.RED);
                // G
                g.drawLine(140, 300, 100, 330);
                g.drawLine(100, 330, 100, 390);
                g.drawLine(100, 390, 150, 390);
                g.drawLine(150, 390, 120, 370);
                g.drawLine(150, 300, 110, 330);
                g.drawLine(110, 330, 110, 390);
                g.drawLine(110, 390, 160, 390);
                g.drawLine(160, 390, 130, 370);
                // A
                g.drawLine(240, 300, 200, 400);
                g.drawLine(240, 300, 280, 400);
                g.drawLine(220, 350, 258, 350);
                g.drawLine(250, 300, 210, 400);
                g.drawLine(250, 300, 290, 400);
                g.drawLine(230, 350, 268, 350);
                // M
                g.drawLine(340, 300, 340, 400);
                g.drawLine(340, 300, 390, 400);
                g.drawLine(390, 400, 440, 300);
                g.drawLine(440, 300, 440, 400);
                //.
                g.drawOval(456, 400, 10, 10);
            }
        };
        t.start();
        try {
            Thread.sleep(4777);
        } catch(Exception e) {e.printStackTrace();}
        game = new Game();
        new Thread(this).start();
    }
}