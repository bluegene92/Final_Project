/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.Main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Bullet extends GameFigure {

    // missile size
    private static final int SIZE = 5;
    private static final int MAX_EXPLOSION_SIZE = 50;
    private float dx; // displacement at each frame
    private float dy; // displacement at each frame

    // public properties for quick access
    public Color color;
    public Point2D.Float target;

    private static final int UNIT_TRAVEL_DISTANCE = 4; // per frame move

    private int size = SIZE;

    /**
     *
     * @param sx start x of the missile
     * @param sy start y of the missile
     * @param tx target x of the missile
     * @param ty target y of the missile
     * @param color color of the missile
     */
    public Bullet(float sx, float sy, float tx, float ty, Color color) {
        super(sx, sy);
        super.state = GameFigureState.BULLET_STATE_LAUNCHED;
        this.target = new Point2D.Float(tx, ty);
        this.color = color;

        double angle = Math.atan2(ty - sy, tx - sx);
        dx = (float) (UNIT_TRAVEL_DISTANCE * Math.cos(angle));
        dy = (float) (UNIT_TRAVEL_DISTANCE * Math.sin(angle));
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(2)); // thickness of the line
//        g.drawOval((int) (super.x - size / 2),
//                (int) (super.y - size / 2),
//                size, size);
        g.fill(new Rectangle2D.Float(super.x - size / 2, 
                super.y - size / 2, 
                size * 2, 
                size / 2));

    }

    @Override
    public void update() {
        updateState();
        if (state == GameFigureState.BULLET_STATE_LAUNCHED) {
            updateLocation();
        } else if (state == GameFigureState.BULLET_STATE_REMOVE) {
            updateSize();
        }
    }

    public void updateLocation() {
        
        super.x += dx;
        super.y += dy;
    }

    public void updateSize() {
        size += 2;
    }

    public void updateState() {
        if (state == GameFigureState.BULLET_STATE_LAUNCHED) {
            double distance = target.distance(super.x, super.y);
   
            // When bullet is out of canvas
            if (super.x > Main.gamePanel.width + 16 ||
                super.x < -10 ||
                super.y > Main.gamePanel.height + 16 ||
                super.y < -10) {
//                System.out.println(Main.gamePanel.width + 16);
//                System.out.println(Main.gamePanel.height + 16);
                state = GameFigureState.BULLET_STATE_REMOVE;
            }
        } else if (state == GameFigureState.BULLET_STATE_REMOVE) {
                state = GameFigureState.STATE_DONE;
        }
    }
    
    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(
                (int) (super.x - size / 2),
                (int) (super.y - size / 2),
                (float) size * 0.9f, (float) size * 0.9f);
    }
}
