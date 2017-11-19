package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Spaceship extends GameFigure {

    private final int WIDTH = 64;
    private final int HEIGHT = 29;
    private final int UNIT_TRAVEL = 2; // per frame
    private float mouseX;
    private float mouseY;
    public int dy = 3;
    private BufferedImage image;
    private List<BufferedImage> spaceshipSprites;
    private int frameNumber = 0;
    public static double imageAngleRad = 0;
    public boolean boosterFlag = false;
    public int missileCharge;
    public int health = 5;
    
    private int direction = 1; // +1: to the right; -1 to the left

    public Spaceship(float x, float y) {
        super(x, y); // origin: upper-left corner
        super.state = GameFigureState.SPACESHIP_STATE_APPEARED;
        super.state = GameFigureState.SPACESHIP_STATE_HEALTH_LEVEL_5;
        missileCharge = 5;
        setState(new ActiveState());
        try {
            image = ImageIO.read(getClass().getResource("f1.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open spaceship.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int cx = 64/ 2;
        int cy = 29 /2;
        AffineTransform oldAT = g.getTransform();
        g.translate(cx + super.x, cy + super.y);
        g.rotate(imageAngleRad);
        g.translate(-cx, -cy);
        g.drawImage(image, 0, 0, null);
        g.setTransform(oldAT);
    }

    
    public void setMouseLocation(float x, float y) {
        this.mouseX = x;
        this.mouseY = y;
    }
    
    /**
     * How the spaceship will move
     */
    @Override
    public void update() {
        if (super.y < mouseY - 10) {
            super.y+= dy;
        } else if (super.y >= mouseY) {
            super.y-= dy;
        }
        
        
        if (boosterFlag && super.x < Main.WIN_WIDTH / 4) {
            super.x += 1;
        } else if (super.x > 30) {
            super.x -= 1;
        }
    }
    
    public void boosterOn() {
        boosterFlag = true;
    }
 
    public void boosterOff() {
        boosterFlag = false;
    }
    
    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(super.x, (int) super.y,
                WIDTH, HEIGHT);
    }

}
