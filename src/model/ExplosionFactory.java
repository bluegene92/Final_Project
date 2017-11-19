package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ExplosionFactory {
    
    private List<BufferedImage> images = new CopyOnWriteArrayList<>();
    private BufferedImage image;
    private int spriteSize = 100;
    public ExplosionFactory() {
        try {
            image = ImageIO.read(getClass().getResource("explosion_sprite.png"));
            for (int i = 0; i < 9; ++i) {
                for (int j = 0; j < 9; ++j) {
                    images.add(image.getSubimage(i*100, j*100, spriteSize, spriteSize));
                }
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open hint.png");
            System.exit(-1);
        }
    }
    
    public Explosion createExplosion(float x, float y) {
        return new Explosion(images, x, y);
    }
    

}
