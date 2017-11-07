package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.GameFigure;
import model.Missile;
import model.Spaceship;
import model.Star;

public class KeyController extends KeyAdapter {
    

    Spaceship spaceship = (Spaceship) Main.gameData.friendFigures.get(0);
        
    @Override
    public void keyPressed(KeyEvent e) {

        
        // horizontal move only 
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            spaceship.boosterOn();
            for (GameFigure f : Main.gameData.stars) {
                Star s = (Star) f;
                s.boosterOn();
            }
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_SPACE) {
            spaceship.boosterOff();
            for (GameFigure f : Main.gameData.stars) {
                Star s = (Star) f;
                s.boosterOff();
            }
        } else if (code == KeyEvent.VK_M) {
            Main.gameData.friendFigures.add(new Missile(
                spaceship.x + 64,
                spaceship.y + (29 / 2),
                MouseController.x, MouseController.y));
        }
        
    }

}
