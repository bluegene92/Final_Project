package model;

import controller.Main;

public class UFODieState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        EnemyUFO ufo = (EnemyUFO) gameFigure;
        ufo.spriteImage = ufo.spriteImage2;
        ufo.y += 3;
        ufo.x += 0.5;
        if (ufo.y > Main.WIN_HEIGHT + 20) {
            ufo.setState(new DoneState());
            Main.gameData.scoreBoard.increaseScore();
        }
    }

}
