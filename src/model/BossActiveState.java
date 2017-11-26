package model;

import controller.Main;

public class BossActiveState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        Boss boss = (Boss) gameFigure;
        if (boss.x >=Main.WIN_WIDTH - 200) {
            boss.x -= 3;
        } else if (boss.x <= Main.WIN_WIDTH - 200) {
            boss.setState(new BossFightState());
        }
    }

}
