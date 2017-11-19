package model;

import controller.Main;
import view.GamePanel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
    public AsteroidFactory asteroidFactory;
    public final List<GameFigure> removeFigures;
    public ExplosionFactory explosionFactory;
    public boolean gameOver = false;
    
    public Explosion explosion;
    
    public GameData() {
        enemyFigures = new CopyOnWriteArrayList<>();
        friendFigures = new CopyOnWriteArrayList<>();
        removeFigures = new CopyOnWriteArrayList<>();
        stars = new CopyOnWriteArrayList<>();
        healthBar = new HealthBar(50, 50);
        asteroidFactory = new AsteroidFactory();
        explosionFactory = new ExplosionFactory();
        
        startButton = new StartButton();
        playAgainButton = new PlayAgainButton();

        spaceship = new Spaceship(30, Main.WIN_HEIGHT / 2);
        friendFigures.add(spaceship);
        scoreBoard = new Score();
        addStars();
        removeHit();
    }
    
    public void addAsteroid() {
        enemyFigures.add(asteroidFactory.getAsteroid());
        System.out.println("adding asteroid");
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
    
    public void createExplosion() {
        
    }

    public void removeHit() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                for (int i = 0; i < friendFigures.size(); i++) {
                    GameFigure ff = friendFigures.get(i);
                    if (ff instanceof Hit || ff instanceof Explosion) {
                        friendFigures.remove(i);
                    }
                }
            }
        }, 0, 300);
    }
    
    public void update() {
        healthBar.update();
        startButton.update();
        for (GameFigure star : stars) {
            star.update();
        }

        
        for (GameFigure g : friendFigures) {
            g.update();
        }
        
        GameFigure f;

        enemyFigures.removeAll(removeFigures);
        friendFigures.removeAll(removeFigures);
        
        for (GameFigure g : enemyFigures) {
            g.update();
        }
        
        for (int i = 0; i < friendFigures.size(); ++i) {
            GameFigure ff = friendFigures.get(i);

        }

        for (int i = 0; i < friendFigures.size(); i++) {
            GameFigure f1 = friendFigures.get(i);
            if (f1 instanceof Bullet) {
                if (f1.myState.getClass().equals(new DoneState().getClass())) {
                    friendFigures.remove(i);
                }
            }

        }
        
        
        for (int i = 0; i < enemyFigures.size(); i++) {
            GameFigure ef = enemyFigures.get(i);
            if (ef instanceof Asteroid) {
                if (ef.myState.getClass().equals(new DoneState().getClass())) {
                    enemyFigures.remove(i);
                }
            }

        }
    }
}
