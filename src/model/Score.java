/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import controller.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Score {

    public int score = 0;
    private BufferedImage image;
    public BufferedImage missileSprite;
    public static BufferedImage missile;
    public Score() {
        try {
            image = ImageIO.read(getClass().getResource("missiles.png"));
            missileSprite = resize(image.getSubimage(0, 0, 350, 80), 350/6, 80/6);
            missile = missileSprite;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open missiles_sprite.png");
            System.exit(-1);
        }        
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 12));
        g.drawString("Score: " + score, 35, 85);
        if (missileSprite != null) {
            g.drawImage(missileSprite, 25, 55, null);
        } else {
            for (int i = 0; i < GameData.spaceship.missileCharge; ++i) {
                g.setColor(Color.CYAN);
                g.fill(new Rectangle2D.Float(35 + (i*10), 55, 10, 5));
            }
        }
    }
    
    public void update() {
        
    }
    
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();
        return dimg;    
    }
}
