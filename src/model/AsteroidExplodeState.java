package model;

import controller.Main;

public class AsteroidExplodeState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
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
