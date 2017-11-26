/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Asteroid extends GameFigure {

    private BufferedImage asteroidImage;
    public float xSpeed = 3;
    public Random rand = new Random();
    public boolean speedFlag = false;
    public static int count = 0;
    private final int MAX = 5;
    private AffineTransform transformer = new AffineTransform();
    public double angle = 360;
    public float width, height;
    public double offset = 0.5; // Speed of rotation
    public float rotateDirection = 0.0f;
    public int asteroidHealth;
    private int asteroidType;
    public float dx = 0.0f;
    public float dy = 0.0f;
    private int size = 1;
    public boolean breakOff = false;
    public boolean isHit = false;
    
    public Asteroid(BufferedImage sprite, float x, float y, int size, int type) {
        super(x, y);
        asteroidHealth = (6 - size) * 2;
        xSpeed = 1 + rand.nextFloat() * (8 - 1);
        asteroidType = type;
        rotateDirection = (float) Math.random();
        asteroidImage = resize(sprite, 128/size, 128/size);
        width = asteroidImage.getWidth();
        height = asteroidImage.getHeight();
        this.size = size;
        myState = new AsteroidLaunchState();
    }
    
    
    public Asteroid(BufferedImage sprite, float sx, float sy, float tx, float ty, int size, int type) {
        super(sx, sy);
        breakOff = true; //Make the asteroid split up randomly
        double angle = Math.atan2(ty-sy, tx-sx);
        xSpeed =  rand.nextInt(4) + 1;
        dx = (float) (xSpeed * Math.cos(angle));
        dy = (float) (xSpeed * Math.sin(angle));
        System.out.println(dx + " " + dy);
        asteroidHealth = (6-size) * 2;
        asteroidImage = resize(sprite, 128/size, 128/size);
        width = asteroidImage.getWidth();
        height = asteroidImage.getHeight();
        this.size = size;
        myState = new AsteroidLaunchState();
        System.out.println("break off");
    }

    
    @Override
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int v = rand.nextInt(4);        
        if (!breakOff) {
            transformer.setToTranslation(super.x, super.y);
        } else {
            transformer.setToTranslation(super.x += dx, super.y += dy);
        }
        transformer.rotate(Math.toRadians(angle), width/2, height/2);
        //g.drawImage(choosenAsteroid, (int)super.x, (int)super.y, null);
        g.drawImage(asteroidImage, transformer, null);
        //g.draw(new Rectangle.Float(super.x + (width/10), super.y + height/10, (float) width*0.8f, (float) height*0.8f));

    }
    
    @Override
    public void setState(State s) {
        myState = s;
    }

    public void rotateCounterClockwise() {
        angle -= offset;
        if (angle <= 0) {
            angle = 360;
        }
    }

    public void rotateClockwise() {
        angle += offset;
        if (angle > 360) {
            angle = 0;
        }
    }
    
    @Override
    public void update() {
        myState.doAction(this);
    }
    
    public void speedUp() {
        speedFlag = true;
    }
    
    public void speedDown() {
        speedFlag = false;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle.Float(super.x + (width/10), super.y + height/10, (float) width*0.8f, (float) height*0.8f);
    }

    @Override
    public void hit(GameFigure gameFigure) {
        if (!isHit) {
            if (gameFigure instanceof Bullet) {
                asteroidHealth--;
                isHit = true;
            } else if (gameFigure instanceof Missile) {
                asteroidHealth -= 5;
                isHit = true;
            }
        }
        
        if (asteroidHealth <= 0) {
            setState(new AsteroidExplodeState());
        }
        System.out.println(asteroidHealth);
    }

}
