package model;

import controller.Main;

public class AsteroidLaunchState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        Asteroid asteroid = (Asteroid) gameFigure;
        if (asteroid.rotateDirection < 0.5) {
            asteroid.rotateCounterClockwise();
        } else {
            asteroid.rotateClockwise();
        }
        
        if (asteroid.speedFlag) {
            asteroid.x-=asteroid.xSpeed*1.8;
        } else {
            if (!asteroid.breakOff) {
                asteroid.x -= asteroid.xSpeed;
            }
        }
        
        if (asteroid.x <= -200) {
            asteroid.rotateDirection = (float) Math.random();
            asteroid.x = Main.WIN_WIDTH + 200;
            asteroid.y = asteroid.rand.nextInt(Main.WIN_HEIGHT);
        }
    }

}
