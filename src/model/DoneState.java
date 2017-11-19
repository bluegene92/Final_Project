package model;

import controller.Main;

public class DoneState implements State {
    
    AsteroidFactory factory = Main.gameData.asteroidFactory;
    @Override
    public void doAction(GameFigure gameFigure) {
        Main.gameData.removeFigures.add(gameFigure);
    }

}
