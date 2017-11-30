package model;

import controller.Main;

public class BossFightState implements State {
    
    int direction = -1; 
    @Override
    public void doAction(GameFigure gameFigure) {
        Boss boss = (Boss) gameFigure;
//        boss.y += direction;
//        
//        
//        if (boss.y < 0 || boss.y > Main.WIN_HEIGHT - boss.height) {
//            direction *= -1;
//        }

        if(boss.y + boss.height/2 < Main.gameData.spaceship.y) {
            boss.y++;
        } else if (boss.y + boss.height/2 > Main.gameData.spaceship.y) {
            boss.y--;
        }        
    }
}
