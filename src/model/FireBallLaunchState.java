package model;

public class FireBallLaunchState implements State {

    @Override
    public void doAction(GameFigure gameFigure) {
        FireBall fireball = (FireBall) gameFigure;
        fireball.x-=5;
    }

}
