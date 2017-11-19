package model;

import controller.Main;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class AsteroidFactory {
    
    private List<BufferedImage> asteroidList;
    private BufferedImage image;
    private Random rand = new Random();
    public AsteroidFactory() {
        asteroidList = new ArrayList<>();
        try {
            image = ImageIO.read(getClass().getResource("asteroid_sprite.png"));
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 2; ++j) {
                    asteroidList.add(image.getSubimage(i*128, j*128, 128, 128));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open spaceship.png");
            System.exit(-1);
        }
    }
    
    
    public Asteroid getAsteroid() {
        int rx = rand.nextInt((Main.WIN_WIDTH + 2000) 
            - (Main.WIN_WIDTH +50) + 1) + Main.WIN_WIDTH + 50;

        int ry = rand.nextInt(Main.WIN_HEIGHT);
        float speed = 1 + rand.nextFloat() * (6 - 1);
        // Select a random asteroid out of four different ones
        int rn = rand.nextInt(3);
        int rSize = rand.nextInt(4) + 1; //1 - 4
        //return new Asteroid(asteroidList.get(rn), rx, ry, rSize);
        // Asteroid(image, x, y, size, asteroidType)
        return new Asteroid(asteroidList.get(0), Main.WIN_WIDTH, 250, 1, 0);
    }
    
    public Asteroid getExplodedAsteroid(float sx, float sy, int orig) {

        int rx = rand.nextInt(Main.WIN_WIDTH);
        int ry = rand.nextInt(Main.WIN_HEIGHT);
        
        if (orig == 1) {
            return new Asteroid(asteroidList.get(0), sx, sy, rx, ry, 2, 0);
        } else if (orig == 2) {
            return new Asteroid(asteroidList.get(0), sx, sy, rx, ry, 3, 0);
        }
        return new Asteroid(asteroidList.get(0), sx, sy, rx, ry, 4, 0);
    }

}
