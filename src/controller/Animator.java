package controller;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import model.Asteroid;
import model.Bullet;
import model.DoneState;
import model.Explosion;
import model.GameFigure;
import model.HitState;
import model.Missile;


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
            if (Main.gameData.friendFigures.get(0).getCollisionBox().intersects(ef.getCollisionBox())) {
                Asteroid asteroid = (Asteroid) ef;
                if (!Main.gameData.healthBar.hit) {
                    Main.gameData.healthBar.loseHealth();
                    Main.gameData.healthBar.hit = true;
                }
                asteroid.setState(new DoneState());
                asteroid.myState.doAction(asteroid);
            }
        }
        
        Main.gameData.healthBar.hit = false;
        
        for (GameFigure ef : Main.gameData.enemyFigures) {
            for (GameFigure ff : Main.gameData.friendFigures) {
                if (ef.getCollisionBox().intersects(ff.getCollisionBox())) {
                    Asteroid asteroid = (Asteroid) ef;
                    // If it is a bullet
                    if (ff instanceof Bullet) {
                        asteroid.asteroidHealth--;
                        ((Bullet) ff).setState(new HitState());
                        ff.myState.doAction(ff);
//                        System.out.println("asteroidHealth: " + asteroid.asteroidHealth);
                        asteroid.setState(new HitState());
                        asteroid.myState.doAction(asteroid);
                    }
                } // End if collision
            } // End inner for
        } // End outer for
        
        
        for (GameFigure ef : Main.gameData.enemyFigures) {
            for (GameFigure ff : Main.gameData.friendFigures) {
                if (ef.getCollisionBox().intersects(ff.getCollisionBox())) {
                    Asteroid asteroid = (Asteroid) ef;
                    if (ff instanceof Missile) {
                        float fx = ff.x;
                        float fy = ff.y;
                        Explosion e = Main.gameData.explosionFactory.createExplosion(fx, fy);
                        Main.gameData.friendFigures.add(e);
                        asteroid.asteroidHealth-=5;
                        ((Missile) ff).setState(new DoneState());
                        ((Missile) ff).myState.doAction(ff);
//                        System.out.println("asteroidHealth: " + asteroid.asteroidHealth);
                        if (asteroid.asteroidHealth <= 0) {
                            ((Asteroid) ef).setState(new DoneState());
                            Main.gameData.scoreBoard.score++;
                        }
                    }
                } // End if collision
            } // End inner for
        } // End outer for
        
        
    } // End processCollisions()


}
