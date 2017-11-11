/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Asteroid extends GameFigure {

    BufferedImage image;
    private List<BufferedImage> asteroidImageList = new ArrayList<>();
    private float xSpeed = 3;
    private Random rand = new Random();
    private BufferedImage scaledImage;
    private BufferedImage choosenAsteroid;
    private boolean speedFlag = false;
    public static int count = 0;
    private final int MAX = 5;
    private AffineTransform transformer = new AffineTransform();
    private double angle = 360;
    private int w, h;
    private double offset = 0.5; // Speed of rotation
    private float rotateDirection = 0.0f;
    
    public Asteroid(float x, float y, float speed) {
        super(x, y);
        this.xSpeed = speed;
        rotateDirection = (float) Math.random();
        try {
            image = ImageIO.read(getClass().getResource("asteroid_sprite.png"));
            
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 2; ++j) {
                    int scale = rand.nextInt(4 - 1 + 1) + 1;
                    scaledImage = resize(image.getSubimage(i*128, j*128, 128, 128), 128/scale, 128/scale);
                    asteroidImageList.add(scaledImage);
                }
            }
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open spaceship.png");
            System.exit(-1);
        }
        
        int v = rand.nextInt(4);
        choosenAsteroid = asteroidImageList.get(v);
        w = choosenAsteroid.getWidth();
        h = choosenAsteroid.getHeight();
    }
    
    @Override
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int v = rand.nextInt(4);
        
        //transformer.setToTranslation(choosenAsteroid.getWidth()/2, choosenAsteroid.getHeight()/2);
        transformer.setToTranslation(super.x, super.y);
        transformer.rotate(Math.toRadians(angle), w/2, h/2);
        //g.drawImage(choosenAsteroid, (int)super.x, (int)super.y, null);
        g.drawImage(choosenAsteroid, transformer, null);
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
            super.x-=xSpeed;
        }
                
        System.out.println("X: " + super.x);
        if (super.x <= -200) {
            System.out.println("reset asteroid to the right");
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

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();
    return dimg;    
    }

    
    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(super.x, (int) super.y,
                20 , 20);
    }

}
