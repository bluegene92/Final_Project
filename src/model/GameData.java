package model;
import controller.Main;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import view.StartButton;

public class GameData {
    public final List<GameFigure> enemyFigures;
    public final List<GameFigure> friendFigures;
    public final List<GameFigure> stars;
    public Spaceship spaceship;
    public Boss boss = new Boss(Main.WIN_WIDTH, Main.WIN_HEIGHT/2);
    public HealthBar healthBar;
    public Score scoreBoard;
    public StartButton startButton;
    public int asteroidCount = 0;
    public AsteroidFactory asteroidFactory;
    public final List<GameFigure> removeFigures;
    private Random rand = new Random();
    public Explosion explosion;
    public ForceField forceField;
    public Mana manaBar;
    
    public GameData() {
        enemyFigures = new CopyOnWriteArrayList<>();
        friendFigures = new CopyOnWriteArrayList<>();
        removeFigures = new CopyOnWriteArrayList<>();
        stars = new CopyOnWriteArrayList<>();
        manaBar = new Mana(50, 25);
        asteroidFactory = new AsteroidFactory();
        startButton = new StartButton();
        spaceship = new Spaceship(30, Main.WIN_HEIGHT / 2);
        friendFigures.add(spaceship);
        scoreBoard = new Score();
        forceField = new ForceField(spaceship.x, spaceship.y);
        addStars();
        removeHit();
    }
    
    public void gameReset() {
        spaceship.healthBar.reset();
        for (int i = 0; i < enemyFigures.size(); ++i) {
            enemyFigures.clear();
        }
    }
    
    public void addAsteroid() {
        enemyFigures.add(asteroidFactory.getAsteroid());
    }

    public void addMediumAsteroid() {
        enemyFigures.add(asteroidFactory.getMediumAsteroid());
    }
    
    public void addSmallAsteroid() {
        enemyFigures.add(asteroidFactory.getSmallAsteroid());
    }
        
    public void addTinyAsteroid() {
        enemyFigures.add(asteroidFactory.getTinyAsteroid());
    }
    
    public void addUFO() {
        int rx = rand.nextInt(Main.WIN_WIDTH + 1) + Main.WIN_WIDTH;
        int ry = rand.nextInt(Main.WIN_HEIGHT);
        enemyFigures.add(new EnemyUFO(rx, ry));
    }
    
    public void addTestUFO() {
        enemyFigures.add(new EnemyUFO(250, 100));
    }
    
    public void fire() {
        boss.fire();
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
    
    public void addBoss() {
        enemyFigures.add(boss);
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
        }, 0, 400);
    }
    
    public void update() {
        spaceship.healthBar.update();
        manaBar.update();
        
        for (GameFigure star : stars) {
            star.update();
        }
        
        for (GameFigure g : friendFigures) {
            g.update();
        }

        enemyFigures.removeAll(removeFigures);
        friendFigures.removeAll(removeFigures);
        
        for (GameFigure g : enemyFigures) {
            g.update();
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
