import java.awt.Image;
import java.awt.image.BufferedImage;

public class Cloud2 {

    public int x = 0;
    public int y = 0;
    private BufferedImage image;
    
    public void update(int movelft) {
        this.x += 2;
    }
    
    public Render getRender() {

        Render r = new Render();
        r.x = x;
        r.y = y;
        
        if (image == null) {
            image = Util.loadImage("lib/cloud.png");     
        }
        r.image = image;

        return r;
    }
}