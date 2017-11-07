package model;

import view.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Bomb extends GameFigure {

    private final int radius;
    private final Color color;
    private int dx = 3;
    private int dy = 3;
    
    private boolean exploded = false;
    
    public Bomb(float x, float y, int radius, Color color) {
        super(x, y);
        super.state = GameFigureState.BOMB_STATE_ADDED;
        this.radius = radius;
        this.color = color;
        this.size = this.radius;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        // Note: use drawOval() to draw outline only
        
        if (super.state != GameFigureState.STATE_DONE) {
            g.fillOval((int)(x - radius), (int)(y - radius), 
                radius * 2, radius * 2);
        } else if (super.state == GameFigureState.STATE_DONE) {
            g.fill(new Ellipse2D.Float(super.x - (radius), super.y, this.size * 2, this.size * 2));
            g.fill(new Ellipse2D.Float(super.x + (radius), super.y, this.size * 2, this.size * 2));
            g.fill(new Ellipse2D.Float(super.x, super.y - (radius * 2), this.size * 2, this.size * 2));
        }
    }

    @Override
    public void update() {

        
        // ball bounces on the wall
        if (super.state != GameFigureState.STATE_DONE) {
            super.x += dx;
            super.y += dy;
        } else {
            
            if (this.size < 15 && !exploded) {
                size += 1;
                if (this.size > 14)
                    exploded = true;
                //System.out.println("size: " + size);
            } else if (exploded) {
                size -= 1;
            }
   
        }

 
            
        if (super.x + radius > GamePanel.width) {
            dx = -dx;
            super.x = GamePanel.width - radius;
        } else if (super.x - radius < 0) {
            dx = -dx;
            super.x = radius;
        }

        if (super.y + radius > GamePanel.height) {
            dy = -dy;
            super.y = GamePanel.height - radius;
        } else if (super.y - radius < 0) {
            dy = -dy;
            super.y = radius;
        }
    }
    
    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(
                (int)(x - radius), 
                (int)(y - radius), 
                (float) (radius * 2) * 0.9f, 
                (float) (radius * 2) * 0.9f);
    }
    
}
