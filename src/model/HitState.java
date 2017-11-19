package model;

import controller.Main;
import java.util.Random;

public class HitState implements State  {

    Random rand = new Random();
    
    @Override
    public void doAction(GameFigure gameFigure) {
        if (gameFigure instanceof Bullet) {
            System.out.println("hit animation");
            float fx = gameFigure.x;
            float fy = gameFigure.y;
            int dif = rand.nextInt(30);
            Hit hit = new Hit(fx, fy - dif);
            Main.gameData.friendFigures.add(hit);
            gameFigure.setState(new DoneState());
        }
    }

}
