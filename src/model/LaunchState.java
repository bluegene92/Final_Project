package model;

public class LaunchState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        if (gameFigure instanceof Bullet) {
            Bullet bullet = (Bullet) gameFigure;
            bullet.updateLocation();
        }
    }

}
