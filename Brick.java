import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Brick {
    
    public int x;
    public int y;
    public int point;
    public boolean step;

    private BufferedImage image;
    
    public Render getRender() {

        Render r = new Render();
        r.x = x;
        r.y = y;

        Random random = new Random();

        int v = random.nextInt(3);
        if(v==0)
            image = Util.loadImage("lib/img1.jpg");
        if(v==1)
            image = Util.loadImage("lib/img2.jpg");
        if(v==2)
            image = Util.loadImage("lib/img3.jpg");

        r.image = image;

        return r;
    }
}
