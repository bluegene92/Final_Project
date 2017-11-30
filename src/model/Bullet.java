package model;
import controller.Main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Bullet extends GameFigure implements CollisionVistable {
    private static final int SIZE = 5;
    private float dx;
    private float dy;
    public Color color;
    public Point2D.Float target;
    private static final int UNIT_TRAVEL_DISTANCE = 8; // per frame move
    private int size = SIZE;
    private BufferedImage image;
    int width;
    int height;
    public Asteroid tempAsteroid;
    public EnemyUFO tempUFO;
    public Boss tempBoss;
    
    public Bullet(float sx, float sy, float tx, float ty, Color color) {
        super(sx, sy);
        this.target = new Point2D.Float(tx, ty);
        this.color = color;
        myState = new BulletLaunchState();
        double angle = Math.atan2(ty - sy, tx - sx);
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
        
        try {
            image = ImageIO.read(getClass().getResource("hit.png"));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open spaceship.png");
            System.exit(-1);
        }  
    }

    @Override
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(color);
        g.setStroke(new BasicStroke(2)); // thickness of the line
        g.fill(new Rectangle2D.Float(super.x - size / 2, 
                super.y - size / 2, 
                size * 2, 
                size / 2));
    }

    @Override
    public void update() {
        myState.doAction(this);
    }
    
    @Override
    public void setState(State s) {
        myState = s;
    }

    public void updateLocation() {
        super.x += dx;
        super.y += dy;
        if (super.x > Main.WIN_WIDTH + 10 || 
            super.x < -10 || 
            super.y < -10 || 
            super.y > Main.WIN_HEIGHT + 10) {
            setState(new DoneState());
            myState.doAction(this);
        }
    }

    public void updateSize() {
        size += 2;
    }
    
    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(super.x - size / 2, 
                super.y - size / 2, 
                size * 2, 
                size / 2);
    }
    
    @Override
    public void hit(GameFigure gameFigure) {
        setState(new BulletHitState());
        myState.doAction(this);
        
        if (gameFigure instanceof Asteroid) {
            tempAsteroid = (Asteroid) gameFigure;
            tempAsteroid.isHit = false;
        } else if (gameFigure instanceof EnemyUFO) {
            tempUFO = (EnemyUFO) gameFigure;
            tempUFO.isHit = false;
        } else if (gameFigure instanceof Boss) {
            tempBoss = (Boss) gameFigure;
            tempBoss.isHit = false;
        }
    }

    @Override
    public void accept(CollisionVisitor v) {
        v.visit(this);
    }
}
