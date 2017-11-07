package model;

import controller.Main;
import view.GamePanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {

    private final int RADIUS = 6;
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> friendFigures;
    public final List<GameFigure> stars;
    public static Spaceship spaceship;
    public HealthBar healthBar;
    
    public GameData() {
        enemyFigures = new CopyOnWriteArrayList<>();
        friendFigures = new CopyOnWriteArrayList<>();
        stars = new CopyOnWriteArrayList<>();
        healthBar = new HealthBar(50, 50);
        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        spaceship = new Spaceship(30, Main.WIN_HEIGHT / 2);
        addStars();
        friendFigures.add(spaceship);
        
    }

    public void addStars() {
            Random rand = new Random();
            for (int i = 0; i < 100; ++i) {
                float rx = rand.nextFloat() * Main.WIN_WIDTH;
                float ry = rand.nextFloat() * Main.WIN_HEIGHT;
                float speed = 1 + rand.nextFloat() * (5 - 1);
                float size = 1 + rand.nextFloat() * (3 - 1);
                stars.add(new Star(rx, ry, speed, size));
        }
    }
    
    public void add(int n) {
        for (int i = 0; i < n; i++) {
            float red = (float) Math.random();
            float green = (float) Math.random();
            float blue = (float) Math.random();
            // adjust if too dark since the background is black
            if (red < 0.5) {
                red += 0.2;
            }
            if (green < 0.5) {
                green += 0.2;
            }
            if (blue < 0.5) {
                blue += 0.2;
            }
            enemyFigures.add(new Bomb(
                    (int) (Math.random() * GamePanel.width),
                    (int) (Math.random() * GamePanel.height),
                    RADIUS,
                    new Color(red, green, blue)));
        }
    }

    public void update() {

        healthBar.update();

        for (GameFigure star : stars) {
            star.update();
        }
        
        for (GameFigure g : friendFigures) {
            g.update();
        }
        
        // no enemy is removed in the program
        // since collision detection is not implemented yet.
        // However, if collision detected, simply set
        // f.state = GameFigure.STATE_DONE
        ArrayList<GameFigure> removeEnemies = new ArrayList<>();
        GameFigure f;
        
        
        
        for (int i = 0; i < enemyFigures.size(); i++) {
            f = enemyFigures.get(i);
            if (f.state == GameFigureState.STATE_DONE && f.y > Main.WIN_HEIGHT - 80) {
                removeEnemies.add(f);
            }
            
            /**
             * If the bomb state is done and its size shrink below 0, remove it
             */
            else if (f.state == GameFigureState.STATE_DONE && f.size < 0) {
                //System.out.println("remove this bomb!");
                enemyFigures.remove(i);
            }
        }

        enemyFigures.removeAll(removeEnemies);

        for (GameFigure g : enemyFigures) {
            g.update();
        }
        
                // Remove bullet or missiles
        for (int i = 0; i < friendFigures.size(); i++) {
            GameFigure f1 = friendFigures.get(i);
            if (f1.state == GameFigureState.STATE_DONE) {
               friendFigures.remove(i);
            }
        }
    }
}
