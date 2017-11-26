package model;

import controller.Main;

public class MissileExplodeState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        Missile missile = (Missile) gameFigure;
        float x = missile.x;
        float y = missile.y;
        Main.gameData.friendFigures.add(new Explosion(x, y));
        missile.setState(new DoneState());
    }

}
