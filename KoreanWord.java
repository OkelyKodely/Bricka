import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class KoreanWord {

    public int x = 0;
    public int y = 0;
    private BufferedImage image;
    public String theSample = "typeItNowFast";
    
    public KoreanWord() {}
    
    public void shuffle() {
        ArrayList<Character> characters = new ArrayList<Character>();
        for(char c:theSample.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(theSample.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        theSample = output.toString();
    }
    
    public void update(int movelft) {
        this.y += 1;
    }
    
    public Render getRender() {

        if(theSample.length() > 0) {
        Render r = new Render();
        r.x = x;
        r.y = y;
        
        //create String object to be converted to image
        String sampleText = theSample;
 
        //create the font you wish to use
        Font font = new Font("Tahoma", Font.PLAIN, 20);
        
        //create the FontRenderContext object which helps us to measure the text
        FontRenderContext frc = new FontRenderContext(null, true, true);
         
        //get the height and width of the text
        Rectangle2D bounds = font.getStringBounds(sampleText, frc);
        int w = (int) bounds.getWidth();
        int h = (int) bounds.getHeight();
        
        //create a BufferedImage object
        BufferedImage image = new BufferedImage(w, h,   BufferedImage.TYPE_INT_RGB);
        
        //calling createGraphics() to get the Graphics2D
        Graphics2D g = image.createGraphics();
        
        //set color and other parameters
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, w, h);
        g.setColor(Color.red);
        g.setFont(font);
             
        try{
            byte[] utf8 = sampleText.getBytes("UTF-8");
            sampleText = new String(utf8);
            g.drawString(sampleText, (float) bounds.getX(), (float) -bounds.getY());
        } catch(UnsupportedEncodingException unsupportedEncodingException) {}
       
        //releasing resources
        g.dispose();
      
        r.image = image;

        return r;
        }
        
        return null;
    }
}