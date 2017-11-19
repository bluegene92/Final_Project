/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Asteroid extends GameFigure {

    private BufferedImage asteroidImage;
    private float xSpeed = 3;
    private Random rand = new Random();
    private boolean speedFlag = false;
    public static int count = 0;
    private final int MAX = 5;
    private AffineTransform transformer = new AffineTransform();
    private double angle = 360;
    public float width, height;
    private double offset = 0.5; // Speed of rotation
    private float rotateDirection = 0.0f;
    public int asteroidHealth;
    private int asteroidType;
    public float dx = 0.0f;
    public float dy = 0.0f;
    private int size = 1;
    public boolean breakOff = false;
    
    public Asteroid(BufferedImage sprite, float x, float y, int size, int type) {
        super(x, y);
        asteroidHealth = (6-size) * 2;
        //float random = min + r.nextFloat() * (max - min);
        xSpeed = 1 + rand.nextFloat() * (8 - 1);
        asteroidType = type;
        rotateDirection = (float) Math.random();
        asteroidImage = resize(sprite, 128/size, 128/size);
        width = asteroidImage.getWidth();
        height = asteroidImage.getHeight();
        this.size = size;
        setState(new ActiveState());
        System.out.println(myState.toString());
        System.out.println("health: " + asteroidHealth);
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
        setState(new ActiveState());
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
        
        if (rotateDirection < 0.5) {
            rotateCounterClockwise();
        } else {
            rotateClockwise();
        }
        
        if (speedFlag) {
            super.x-=xSpeed*1.8;
        } else {
            if (!breakOff) {
                super.x -= xSpeed;
            }
        }
        


        if (super.x <= -200) {
            rotateDirection = (float) Math.random();
            super.x = Main.WIN_WIDTH + 200;
            super.y = rand.nextInt(Main.WIN_HEIGHT);
        }
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

}
