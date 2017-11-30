package model;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class FireBall extends GameFigure {
    private BufferedImage image;
    private int width;
    private int height;
    private float dx;
    private float dy;
    private static final int UNIT_TRAVEL_DISTANCE = 4;
    private AffineTransform transformer = new AffineTransform();
    public static double imageAngleRad = 0;
    private double angle;
//    imageAngleRad = Math.atan2(dy, dx);
    public FireBall(float sx, float sy, float tx, float ty) {
        super(sx, sy);
        myState = new FireBallLaunchState();
        angle = Math.atan2(ty - sy, tx - sx);
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
        try {
            image = ImageIO.read(getClass().getResource("fireball.png"));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open fire.png");
            System.exit(-1);            
        }
    }

    @Override
    public void render(Graphics2D g) {
        transformer.setToTranslation(super.x += dx, super.y += dy);
        transformer.rotate(0);
        g.drawImage(image, transformer, null);
        g.draw(new Rectangle2D.Float(x, y + 2, width * 0.8f, height * 0.8f));
    }

    @Override
    public void update() {
        myState.doAction(this);
    }

    @Override
    public void hit(GameFigure gameFigure) {
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(x, y + 2, width * 0.8f, height * 0.8f);
    }
}
