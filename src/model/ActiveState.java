package model;
public class ActiveState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        if (gameFigure instanceof Missile) {
            ((Missile) gameFigure).updateLocation();
        }
    }

}
