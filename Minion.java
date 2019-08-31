import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Minion {

    public String direction;
    
    public boolean startOver;
    
    public int x;
    public int y;
    public int width;
    public int height;

    // y velocity
    public double yvel;
    public double gravity;

    // delay between key presses
    private int jumpDelay;

    private BufferedImage image;
    private Keyboard keyboard;
    
    public int startGame = 0;
    private boolean onbrick = false;
    public int point;
    
    public Minion () {
        x = 100;
        y = 588;
        yvel = 0;
        width = 45;
        height = 32;
        gravity = 0.5;
        point=0;
        jumpDelay = 0;
        startGame = 1;

        keyboard = Keyboard.getInstance();
        
        Random rd = new Random();
        int v = rd.nextInt(2);
        if(v == 0)
            direction = "left";
        else if(v == 1)
            direction = "right";
    }

    public void update (ArrayList<Brick> bricks) {
        yvel += gravity;

        for(Brick brick : bricks)
            if(yvel > 0 && !keyboard.isDown(KeyEvent.VK_SPACE) && brick.y >= y+25 && brick.y <= y+35 && brick.x >= x-30 && brick.x <= x+36)
            {
                yvel = 0;
                onbrick = true;
            }

        if(yvel == 0) {
            if( direction.equals("left"))
                x-=12;
            else if( direction.equals("right"))
                x+=12;
        }
        
        y += (int) yvel;
    }

    public Render getRender() {

        Render r = new Render();
        r.x = x;
        r.y = y;

        if (image == null) {
            image = Util.loadImage("lib/minion.png");     
        }
        r.image = image;

        return r;
    }
}