package controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import model.Asteroid;
import model.AsteroidExplodeState;
import model.Boss;
import model.Bullet;
import model.DoneState;
import model.Explosion;
import model.GameFigure;
import model.BulletHitState;
import model.EnemyUFO;
import model.Missile;
import model.UFODieState;


public class Animator implements Runnable {

    public boolean running = true;
    private final int FRAMES_PER_SECOND = 60;
    public boolean gameStart = false;
    Random rand = new Random();
    @Override
    public void run() {

        while (running) {
            long startTime = System.currentTimeMillis();
            
            //processCollisions();
            if (gameStart) {
                processCollisions();
                Main.gameData.update();
            }
            Main.gamePanel.gameRender();
            Main.gamePanel.printScreen();

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);

            if (sleepTime > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {

                }
            }
        }
        System.exit(0);
    }
    
    private void processCollisions() {
        for (GameFigure ef : Main.gameData.enemyFigures) {
            for (GameFigure ff : Main.gameData.friendFigures) {
                if (ef.getCollisionBox().intersects(ff.getCollisionBox())) {
                    ef.hit(ff);
                    ff.hit(ef);
                }
            }
        }
    } // End processCollisions()
}
