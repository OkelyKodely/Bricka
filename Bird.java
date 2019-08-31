import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Bird {

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
    
    public Bird () {
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
    }

    public int update (ArrayList<Brick> bricks, int continits) {

        if(startGame == 1) {
            onbrick = true;
            startGame = 2;
        }
        
        yvel += gravity;

        if (jumpDelay > 0)
            jumpDelay--;

        if (/*y == 688 && */onbrick && keyboard.isDown(KeyEvent.VK_SPACE) && jumpDelay <= 0) {
            yvel = -13;
            jumpDelay = 10;
            onbrick = false;
        }
        
        if(keyboard.isDown(KeyEvent.VK_LEFT) && x > 0) {
            x -= 5;
        } else if(keyboard.isDown(KeyEvent.VK_RIGHT) && x < 500-20-25) {
            x += 5;
        } else if(keyboard.isDown(KeyEvent.VK_LEFT) && x <= 170) {
        } else if(keyboard.isDown(KeyEvent.VK_RIGHT) && x >= 250) {
        }
        
        for(Brick brick : bricks)
            if(yvel > 0 && !keyboard.isDown(KeyEvent.VK_SPACE) && brick.y >= y+25 && brick.y <= y+35 && brick.x >= x-30 && brick.x <= x+36)
            {
                yvel = 0;
                onbrick = true;
                
                if(!brick.step)
                    this.point++;

                if(brick.point == 0)
                    brick.step = true;
                
                if(brick.point == 0)
                    brick.point = 1;
            }

        if(y > 670) {
            onbrick = true;
            startOver = true;
        }
        
        y += (int) yvel;
        
        for(Brick brick: bricks) {
            brick.y += 2;
            if(continits >= 10 && continits <= 20) {
                Random rd = new Random();
                int vl = rd.nextInt(2);
                if(vl == 0)
                    brick.x+=3;
                else
                    brick.x-=3;
            }
//            if(continits > 20) {
//                Random rd = new Random();
//                int vl = rd.nextInt(2);
//                if(vl == 0)
//                    brick.x+=1;
//                else
//                    brick.x-=1;
//            }
        }
        
        return point ;
    }

    public Render getRender() {

        Render r = new Render();
        r.x = x;
        r.y = y;

        if (image == null) {
            image = Util.loadImage("lib/bird.png");     
        }
        r.image = image;

        return r;
    }
}