package model;

import controller.Main;
import java.util.Random;

public class BulletHitState implements State  {

    Random rand = new Random();
    
    @Override
    public void doAction(GameFigure gameFigure) {
        Bullet bullet = (Bullet) gameFigure;
        System.out.println("hit animation");
        float fx = bullet.x;
        float fy = bullet.y;
        int dif = rand.nextInt(30);
        Hit hit = new Hit(fx, fy - dif);
        Main.gameData.friendFigures.add(hit);
        bullet.setState(new DoneState());
    }

}
