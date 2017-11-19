package model;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Explosion extends GameFigure {
    public List<BufferedImage> images;
    private int width;
    private int height;
    private int index;
    private double timeCount;
    public State myState = new ActiveState();
    public Explosion(List<BufferedImage> images, float x, float y) {
        super(x, y);
        this.images = images;
    }
    
    public void render(Graphics2D g) {
        g.drawImage(images.get(20), (int) x, (int) y, null);
    }
    
    @Override
    public void update() {
//        myState.doAction(this);
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(x, y, 100, 100);
    }
}
