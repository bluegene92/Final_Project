package model;
import controller.Main;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class EnemyUFO extends GameFigure {
    
    private BufferedImage image;
    public BufferedImage spriteImage;
    private BufferedImage image2;
    public BufferedImage spriteImage2;
    private int width;
    private int height;
    public float dx;
    public float dy;
    private float fx;
    private float fy;
    private Random rand = new Random();
    private float UNIT_TRAVEL = 2;
    public boolean reachDestination = false;
    public int health = 2;
    public boolean isDead = false;
    public boolean isHit = false;
    
    public EnemyUFO(float x, float y) {
        super(x, y);
        myState = new UFOActiveState();
        try {
            image = ImageIO.read(getClass().getResource("ufo.png"));
            image2 = ImageIO.read(getClass().getResource("ufo2.png"));
            spriteImage = image.getSubimage(0, 0, 40, 30);
            spriteImage2 = image2.getSubimage(0, 0, 40, 30);
            width = spriteImage.getWidth();
            height = spriteImage.getHeight();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open ufo.png");
            System.exit(-1);
        }
        fx = rand.nextInt(Main.WIN_WIDTH);
        fy = rand.nextInt(Main.WIN_HEIGHT);
        double angle = Math.atan2(fy - super.y, fx - super.x);
        dx = (float) (UNIT_TRAVEL * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL * Math.sin(angle));
        
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(spriteImage, (int) x, (int) y, null);
    }
    
    @Override
    public void setState(State s) {
        myState = s;
    }
    
    public void generateDestination() {
        fx = rand.nextInt(Main.WIN_WIDTH);
        fy = rand.nextInt(Main.WIN_HEIGHT);
        double angle = Math.atan2(fy - super.y, fx - super.x);
        dx = (float) (UNIT_TRAVEL * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL * Math.sin(angle));
        reachDestination = false;
    }

    @Override
    public void update() {
        myState.doAction(this);
    }
    
    public void move() {
        if (!reachDestination) {
            super.x += dx;
            super.y += dy;    
        } else if (isDead) {
            super.y += 1;
        }

        if (reachDestination()) {
            reachDestination = true;
            generateDestination();
        }        
    }
    
    public boolean reachDestination() {
        int distX = (int) Math.abs(super.x - fx);
        int distY = (int) Math.abs(super.y - fy);
        if (distX < 2 && distY < 2) {
            return true;
        }
        return false;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(x, y, width, height);
    }
    
    @Override
    public void hit(GameFigure gameFigure) {
        if (!isHit) {
            if (gameFigure instanceof Bullet) {
                health--;
                isHit = true;
            } else if (gameFigure instanceof Missile) {
                health -= 5;
                isHit = true;
            }
        }
        
        if (health <= 0) {
            setState(new UFODieState());
        }
    }
}
