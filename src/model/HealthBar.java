
package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class HealthBar extends GameFigure {

    
    public int health = 5;
    private List<BufferedImage> healthBarSprites;
    private BufferedImage image;
    private BufferedImage healthBarImage;
    private int state;
    public HealthBar(float x, float y) {
        super(x, y);
        healthBarSprites = new CopyOnWriteArrayList<>();
        state = GameFigureState.SPACESHIP_STATE_HEALTH_LEVEL_5;
        try {
            image = ImageIO.read(getClass().getResource("health_bar.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open spaceship.png");
            System.exit(-1);
        }
        
        for (int i = 0; i < 6; ++i) {
            BufferedImage tmp = resize(image.getSubimage(0, i*120, 450, 120), 450/6, 120/6);
            healthBarSprites.add(tmp);
        }
        healthBarImage = healthBarSprites.get(0);
    }
    
    

    public void loseHealth() {
        
    }
    

    @Override
    public void render(Graphics2D g) {
        g.drawImage(healthBarImage, 30, 30, null);
    }

    
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();
    return dimg;    
    }


    
    @Override
    public void update() {

        if (state == GameFigureState.SPACESHIP_STATE_HEALTH_LEVEL_5) {
            healthBarImage = healthBarSprites.get(0);
        } else if (state == GameFigureState.SPACESHIP_STATE_HEALTH_LEVEL_4) {
            healthBarImage = healthBarSprites.get(1);
        } else if (state == GameFigureState.SPACESHIP_STATE_HEALTH_LEVEL_3) {
            healthBarImage = healthBarSprites.get(2);
        } else if (state == GameFigureState.SPACESHIP_STATE_HEALTH_LEVEL_2) {
            healthBarImage = healthBarSprites.get(3);
        } else if (state == GameFigureState.SPACESHIP_STATE_HEALTH_LEVEL_1) {
            healthBarImage = healthBarSprites.get(4);
        } else if (state == GameFigureState.SPACESHIP_STATE_HEALTH_LEVEL_0) {
            healthBarImage = healthBarSprites.get(5);
        } 
        
    }

    @Override
    public Rectangle2D getCollisionBox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
