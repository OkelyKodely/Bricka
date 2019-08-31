import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game implements KeyListener {
    
    public int continits = 0;

    public int point;
    
    public boolean startAgain;
    
    Random random = new Random();
    
    public Keyboard keyboard;

    Background background;
        
    Bird bird;
    
    KoreanWord koreanWord;
    
    ArrayList<Cloud> clouds;
    ArrayList<Cloud2> clouds2;

    ArrayList<Brick> bricks;
    
    ArrayList<Minion> minions;

    boolean started = true;

    public void setStartOver(boolean flag) {
        bird.startOver = flag;
    }
    
    public void keyTyped(KeyEvent e) {
        
    }
    
    public void keyPressed(KeyEvent e) {
        
    }

    public void keyReleased(KeyEvent e) {
        for(int i=0; i<koreanWord.theSample.length(); i++) {
            if(e.getKeyChar() == koreanWord.theSample.charAt(i)) {
                koreanWord.theSample = koreanWord.theSample.substring(0, i) + koreanWord.theSample.substring(i+1, koreanWord.theSample.length());
                this.point += 15;
                this.bird.point = this.point;
            }
        }
    }

    public Game() {
        App.frame.addKeyListener(this);
        bird = new Bird();
        koreanWord = new KoreanWord();
        background = new Background();
        bricks = new ArrayList<Brick>();
        clouds2 = new ArrayList<Cloud2>();
        clouds = new ArrayList<Cloud>();
        minions = new ArrayList<Minion>();
        keyboard = Keyboard.getInstance();
        
        Random random = new Random();
        
        int v = random.nextInt(340);
        
        koreanWord.x = v;
        
        initClouds();
        initClouds2();
    }
    
    public void initClouds() {
        int f = 15;
        for(int x=0; x<f; x++) {
            Cloud cloud = new Cloud();
            cloud.x = 510 + random.nextInt(500);
            Random ra = new Random();
            cloud.y = -random.nextInt(100) + random.nextInt(580);
            clouds.add(cloud);
        }
    }

    public void initClouds2() {
        int f = 15;
        for(int x=0; x<f; x++) {
            Cloud2 cloud = new Cloud2();
            cloud.x = -1010 + random.nextInt(500);
            cloud.y = -random.nextInt(100) + random.nextInt(580);
            clouds2.add(cloud);
        }
    }

    public void initBricks() {
        int f = 5;
        if(continits < 20)
        for(int x=0; x<f; x++) {
            Brick brick = new Brick();
            brick.x = random.nextInt(440);
            brick.y = -random.nextInt(100) + random.nextInt(600);
            bricks.add(brick);
        }
        if(continits >= 20)
        for(int x=0; x<15; x++) {
            Brick brick = new Brick();
            brick.x = random.nextInt(440);
            brick.y = -random.nextInt(100) + random.nextInt(600);
            bricks.add(brick);
            Brick brick2 = new Brick();
            brick2.x = brick.x + 30;
            bricks.add(brick2);
            Random rd = new Random();
            int v = rd.nextInt(3);
            if(v == 0)
            {
                Minion minion = new Minion();
                minion.x = brick.x;
                minion.y = brick.y;
                minions.add(minion);
            }
        }
        this.continits++;
    }

    public void update() {
        Random random = new Random();
        int randomVl = random.nextInt(2);
        boolean g2 = false;
        boolean g = false;
        int count = 0;
        for(Cloud cloud: clouds) {
            g = false;
            if(cloud.x < -50)
            {
                g = true;
            }
            cloud.update(randomVl);
        }
        for(Cloud2 cloud: clouds2) {
            g2 = false;
            if(cloud.x > 600)
            {
                g2 = true;
            }
            cloud.update(randomVl);
        }
        if(g2 || g) {
            initClouds();
            initClouds2();
        }
        g = false;
        count = 0;
        g2 = false;
        randomVl = random.nextInt(bricks.size());
        for(Brick brick: bricks) {
            if(count == randomVl)
            {
                if(started) {
                    bird.x = brick.x;
                    bird.y = brick.y - bird.height;
                    started = false;
                }
            }
            g = false;
            if(brick.y > 300)
                g = true;
            count++;
        }
        if(g) {
            initBricks();
        }
        if(koreanWord.y > 800 || koreanWord.theSample.length() == 0){
            if(koreanWord.x != -666 && koreanWord.theSample.length() == 0){
                this.koreanWord.x = -666;
                this.point += 200;
                this.bird.point = point;
            }
            koreanWord = new KoreanWord();
            koreanWord.shuffle();
            Random random1 = new Random();
            int v = random1.nextInt(340);
            koreanWord.x = v;
        } else if (koreanWord.theSample.length() != 0) {
            this.koreanWord.update(0);
        }
        this.point = bird.update(bricks, continits);
        this.startAgain = bird.startOver;
        for(int i=0; i<minions.size(); i++) {
            minions.get(i).update(bricks);
            if(bird.yvel > 0 && !keyboard.isDown(KeyEvent.VK_SPACE) && minions.get(i).y >= bird.y+25 && minions.get(i).y <= bird.y+35 && minions.get(i).x >= bird.x-30 && minions.get(i).x <= bird.x+36)
            {
                this.point += 10;
                bird.point = this.point;
                minions.remove(minions.get(i));
            }
        }
    }
    
    public ArrayList<Render> getRenders() {
        try {
            ArrayList<Render> renders = new ArrayList<Render>();
            renders.add(background.getRender());
            for(Cloud2 cloud2 : clouds2)
                renders.add(cloud2.getRender());
            for(Cloud cloud : clouds)
                renders.add(cloud.getRender());
            for(Brick brick : bricks)
                renders.add(brick.getRender());
            for(Minion minion : minions)
                renders.add(minion.getRender());
            renders.add(koreanWord.getRender());
            renders.add(bird.getRender());
            return renders;
        } catch(Exception e) {
            return null;
        }
    }
}