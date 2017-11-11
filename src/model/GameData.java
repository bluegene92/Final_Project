package model;

import controller.Main;
import view.GamePanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import view.PlayAgainButton;
import view.StartButton;

public class GameData {

    private final int RADIUS = 6;
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> friendFigures;
    public final List<GameFigure> stars;
    public static Spaceship spaceship;
    public HealthBar healthBar;
    public Score scoreBoard;
    public StartButton startButton;
    public PlayAgainButton playAgainButton;
    public int asteroidCount = 0;
    private Random rand = new Random();
    
    
    public GameData() {
        enemyFigures = new CopyOnWriteArrayList<>();
        friendFigures = new CopyOnWriteArrayList<>();
        stars = new CopyOnWriteArrayList<>();
        healthBar = new HealthBar(50, 50);
        scoreBoard = new Score();
        
        startButton = new StartButton();
        playAgainButton = new PlayAgainButton();
        
        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        spaceship = new Spaceship(30, Main.WIN_HEIGHT / 2);
        addStars();
        addAsteroid();
        friendFigures.add(spaceship);
    }
    
    public void addAsteroid() {
        for (int i = 0; i < 2; ++i) {
            int rx = rand.nextInt((Main.WIN_WIDTH + 2000) 
            - (Main.WIN_WIDTH +50) + 1) + Main.WIN_WIDTH + 50;

            int ry = rand.nextInt(Main.WIN_HEIGHT);
            float speed = 1 + rand.nextFloat() * (6 - 1);

            enemyFigures.add(new Asteroid(rx, ry, speed));
            asteroidCount++;
        }
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
