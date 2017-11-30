package model;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Explosion extends GameFigure {
    private BufferedImage image;
    private int width;
    private int height;
    public Explosion(float x, float y) {
        super(x, y);
        myState = new ActiveState();
        try {
            image = ImageIO.read(getClass().getResource("explosion.png"));
            image = resize(image, image.getWidth()/8, image.getHeight()/8);
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open explosion.png");
            System.exit(-1);
        }
    }
    
    public void render(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);
    }
    
    @Override
    public void update() {
        myState.doAction(this);
    }
    
    @Override
    public void setState(State s) {
        myState = s;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(x, y, 100, 100);
    }
    
    @Override
    public void hit(GameFigure gameFigure) {
    }    
}
