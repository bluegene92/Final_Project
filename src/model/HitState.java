package model;

import controller.Main;
import java.util.Random;

public class HitState implements State  {

    Random rand = new Random();
    
    @Override
    public void doAction(GameFigure gameFigure) {
        if (gameFigure instanceof Bullet) {
            System.out.println("hit animation");
            float fx = gameFigure.x;
            float fy = gameFigure.y;
            int dif = rand.nextInt(30);
            Hit hit = new Hit(fx, fy - dif);
            Main.gameData.friendFigures.add(hit);
            gameFigure.setState(new DoneState());
        }
        
        
        if (gameFigure instanceof Asteroid) {
            Asteroid asteroid = (Asteroid) gameFigure;
            if (asteroid.asteroidHealth <= 0) {

            // If large asteroid (128)
            if (asteroid.width > 127) {
                Main.gameData.enemyFigures.add(Main.gameData.asteroidFactory.getExplodedAsteroid(asteroid.x, asteroid.y, 1));
                Main.gameData.enemyFigures.add(Main.gameData.asteroidFactory.getExplodedAsteroid(asteroid.x, asteroid.y, 1));
                Main.gameData.enemyFigures.add(Main.gameData.asteroidFactory.getExplodedAsteroid(asteroid.x, asteroid.y, 1));
            } else if (asteroid.width > 63) {
                Main.gameData.enemyFigures.add(Main.gameData.asteroidFactory.getExplodedAsteroid(asteroid.x, asteroid.y, 2));
                Main.gameData.enemyFigures.add(Main.gameData.asteroidFactory.getExplodedAsteroid(asteroid.x, asteroid.y, 2));
            } else if (asteroid.width > 41) {
                Main.gameData.enemyFigures.add(Main.gameData.asteroidFactory.getExplodedAsteroid(asteroid.x, asteroid.y, 3));
            }

            asteroid.setState(new DoneState());
            Main.gameData.scoreBoard.score++;
        } 
        }
    }

}
