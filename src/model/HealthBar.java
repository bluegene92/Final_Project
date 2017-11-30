
package model;

import controller.Main;
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
    private List<BufferedImage> healthBarSprites;
    private BufferedImage image;
    private BufferedImage healthBarImage;
    public int index;
    
    public HealthBar(float x, float y) {
        super(x, y);
        healthBarSprites = new CopyOnWriteArrayList<>();
        index = 0;
        
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

    @Override
    public void render(Graphics2D g) {
        g.drawImage(healthBarImage, 30, 30, null);
    }
    
    public void reset() {
        index = 0;
    }



    
    @Override
    public void update() {
        healthBarImage = healthBarSprites.get(index);
    }

    @Override
    public Rectangle2D getCollisionBox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void hit(GameFigure gameFigure) {
    }    
}
