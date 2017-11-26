package model;

import controller.Main;

public class UFOActiveState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        EnemyUFO ufo = (EnemyUFO) gameFigure;
        ufo.move();
    }

}
