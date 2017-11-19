package model;
public class ActiveState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        if (gameFigure instanceof Bullet) {
            ((Bullet) gameFigure).updateLocation();
        } else if (gameFigure instanceof Missile) {
            ((Missile) gameFigure).updateLocation();
        }
    }

}
