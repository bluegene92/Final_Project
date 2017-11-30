package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import model.Asteroid;
import model.GameFigure;
import model.Missile;
import model.Spaceship;
import model.Star;

public class KeyController extends KeyAdapter {
    Spaceship spaceship = (Spaceship) Main.gameData.friendFigures.get(0);
        
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (Main.animator.gameStart) {
            if (code == KeyEvent.VK_SPACE) {
                spaceship.boosterOn();
                for (GameFigure f : Main.gameData.stars) {
                    Star s = (Star) f;
                    s.boosterOn();
                }

                for (GameFigure a : Main.gameData.enemyFigures) {
                    if (a instanceof Asteroid) {
                        Asteroid asteroid = (Asteroid) a;
                        asteroid.speedUp();
                    }
                }
            } else if (code == KeyEvent.VK_F) {
                spaceship.turnOnForceField();
            } 
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (Main.animator.gameStart) {
            if (code == KeyEvent.VK_SPACE) {
                spaceship.boosterOff();
                for (GameFigure f : Main.gameData.stars) {
                    Star s = (Star) f;
                    s.boosterOff();
                }
                for (GameFigure a : Main.gameData.enemyFigures) {

                    if (a instanceof Asteroid) {
                        Asteroid asteroid = (Asteroid) a;
                        asteroid.speedDown();
                    }
                }

            } else if (code == KeyEvent.VK_M) {
                Main.gameData.friendFigures.add(new Missile(
                    spaceship.x + 64,
                    spaceship.y + (29 / 2),
                    MouseController.x, MouseController.y));
            } else if (code == KeyEvent.VK_1) {
                Main.gameData.addAsteroid();
            } else if (code == KeyEvent.VK_2) {
                Main.gameData.addMediumAsteroid();
            } else if (code == KeyEvent.VK_3) {
                Main.gameData.addSmallAsteroid();
            } else if (code == KeyEvent.VK_4) {
                Main.gameData.addTinyAsteroid();
            } else if (code == KeyEvent.VK_U) {
                Main.gameData.addUFO();
            } else if (code == KeyEvent.VK_5) {
                Main.gameData.addTestUFO();
            } else if (code == KeyEvent.VK_B) {
                Main.gameData.addBoss();
            } else if (code == KeyEvent.VK_F) {
                spaceship.turnOffForceField();
            } else if (code == KeyEvent.VK_6) {
                Main.gameData.fire();
            }
        } // End if(gameStart)
    } // End keyReleased()
} // End class KeyController
