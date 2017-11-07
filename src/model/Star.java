/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.Main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Star extends GameFigure {

    float x;
    float y;
    float speed;
    float size;
    boolean boosterFlag = false;
    
    public Star(float x, float y, float speed, float size) {
        super(x, y);
        this.speed = speed;
        this.size = size;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill(new Ellipse2D.Float(super.x, super.y, size, size));
    }

    @Override
    public void update() {
        if (boosterFlag) {
            super.x -= ((speed / 2) + size) * 0.8;
        }
        super.x -= (speed / 2) + size;
        if (super.x < -10) {
            super.x = Main.WIN_WIDTH + 10;
        }
    }
    
    public void boosterOn() {
        boosterFlag = true;
    }
    
    public void boosterOff() {
        boosterFlag = false;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
