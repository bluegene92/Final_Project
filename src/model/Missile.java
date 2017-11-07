package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Missile extends GameFigure {

    // missile size
    private static final int SIZE = 5;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame

    // public properties for quick access
    public Color color;
    public Point2D.Float target;

    private BufferedImage image;
    private List<BufferedImage> missileSprites;
    private int size = SIZE;
    private float xSpeed = 2;
    private BufferedImage smallMissile;
    
    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public Missile(float sx, float sy, float tx, float ty) {
        super(sx, sy);
        super.state = GameFigureState.MISSILE_STATE_LAUNCHED;
        missileSprites = new CopyOnWriteArrayList<>();
        try {
            image = ImageIO.read(getClass().getResource("missiles.png"));
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open spaceship.png");
            System.exit(-1);
        }
        
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 2; ++j) {
                missileSprites.add(image.getSubimage(i*350, j*80, 350, 80));
            }
        }
        smallMissile = resize(missileSprites.get(1), 350 / 8, 80 / 8);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(smallMissile, (int) super.x, (int) super.y, null);
    }

    @Override
    public void update() {
        updateState();
        if (state == GameFigureState.MISSILE_STATE_LAUNCHED) {
            updateLocation();
        } else if (state == GameFigureState.MISSILE_STATE_EXPLODED) {
           //Explosion animation
            
            
        }
    }

    
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();
    return dimg;    
    }
    
    public void updateLocation() {
        super.x += xSpeed;
        xSpeed *= 1.05;
    }

    public void updateState() {
        if (state == GameFigureState.MISSILE_STATE_LAUNCHED) {
            if (super.x > Main.WIN_WIDTH + 10) {
                state = GameFigureState.STATE_DONE;
            }
        } else if (state == GameFigureState.MISSILE_STATE_EXPLODED) {
      
            //draw explosion at the missle location
            
            
            
        }
    }
    
    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(
                (int) (super.x - size / 2),
                (int) (super.y - size / 2),
                (float) size * 0.9f, (float) size * 0.9f);
    }

}
