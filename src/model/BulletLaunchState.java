package model;

public class BulletLaunchState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        ((Bullet) gameFigure).updateLocation();
    }

}
