package model;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Hit extends GameFigure {
    private BufferedImage image;
    private int width;
    private int height;
    private AffineTransform transformer = new AffineTransform();
    private Random rand = new Random();
    private double angle = 360;
    public Hit(float x, float y) {
        super(x, y);
        angle = rand.nextInt(360);
        try {
            image = ImageIO.read(getClass().getResource("hit.png"));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open hint.png");
            System.exit(-1);
        }          
    }

    @Override
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        transformer.setToTranslation(super.x, super.y);
        transformer.rotate(Math.toRadians(angle), width/2, height/2);
        
        g.drawImage(image, transformer, null);
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(x, y, width, height);
    }
    @Override
    public void hit(GameFigure gameFigure) {
    }    
}
