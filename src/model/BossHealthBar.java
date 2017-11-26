package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class BossHealthBar extends GameFigure {

    public int health = 200;
    public int healthBackground = 200;
    public BossHealthBar(float x, float y) {
        super(x, y);
    }
    
    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Float(x, y, healthBackground, 10));
        g.setColor(Color.ORANGE);
        g.fill(new Rectangle2D.Float(x, y, health, 10));
    }

    @Override
    public void update() {}

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(x, y, health, 10);
    }

    @Override
    public void hit(GameFigure gameFigure) {
    }

}
