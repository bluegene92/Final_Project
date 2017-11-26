package model;

import controller.Main;

public class MissileLaunchState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        Missile missile = (Missile) gameFigure;
        missile.updateLocation();
        if (missile.x > Main.WIN_WIDTH + 50) {
            missile.setState(new DoneState());
            missile.myState.doAction(missile);
        }
    }
}
