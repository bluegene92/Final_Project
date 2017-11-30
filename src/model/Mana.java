package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Mana extends GameFigure {
    
    public int mana = 50;
    public Mana(float x, float y) {
        super(x, y);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fill(new Rectangle2D.Float(x, y, mana, 4));
    }

    @Override
    public void update() {
    }

    @Override
    public void hit(GameFigure gameFigure) {
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(50, 50, 50, 50);
    }

}
