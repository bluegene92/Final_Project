package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;
import model.Bullet;
import model.GameFigure;
import model.Hit;
import model.Missile;
import model.Score;
import model.Spaceship;

public class MouseController extends MouseAdapter {
    
    public static int x;
    public static int y;
    volatile private boolean mouseDown = false;
    Spaceship spaceship = (Spaceship) Main.gameData.friendFigures.get(0);
    private int timeUntilShootMissile = 2;
    
    public MouseController() {
        enableMissile();
    }
    
    public void enableMissile() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (spaceship.missileCharge < timeUntilShootMissile) {
                    spaceship.missileCharge++;
                    System.out.println("charging: " + spaceship.missileCharge);
                }
                
                if (spaceship.missileCharge >= timeUntilShootMissile) {
                    Main.gameData.scoreBoard.missileSprite = Score.missile;
                }
            }
        }, 0, 1000);
    }
    
    
    @Override
    public void mousePressed(MouseEvent me) {
        int px = x;
        int py = y;
        
        
        if (SwingUtilities.isLeftMouseButton(me)) {
            
            // If the game haven't start
            if (!Main.animator.gameStart && !Main.gameData.gameOver) {
                if (px > Main.gamePanel.getWidth()/2 &&
                px < Main.gamePanel.getWidth()/2 + 100 &&
                py > Main.gamePanel.getHeight()/4 && 
                py < Main.gamePanel.getHeight()/4 + 40) {
                    Main.animator.gameStart = true;
                    Main.gameData.gameOver = false;
                }
            }
            
            
            
            Bullet bullet = new Bullet(
                spaceship.x + 64,
                spaceship.y + (29 / 2),
                px, py,
                Color.YELLOW);
            
            // Shoot only if the game started
            if (Main.animator.gameStart) {
                Main.gameData.friendFigures.add(bullet);
            }
        } else if (SwingUtilities.isRightMouseButton(me)) {
            // Shoot only if the game started
            if (Main.animator.gameStart && spaceship.missileCharge >= timeUntilShootMissile) {
                Missile missile = new Missile(
                    spaceship.x + 64,
                    spaceship.y + (29 / 2),
                    px, py);
                Main.gameData.friendFigures.add(missile);
                spaceship.missileCharge = 0;
                Main.gameData.scoreBoard.missileSprite = null;
            }
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        Spaceship spaceship = (Spaceship) Main.gameData.friendFigures.get(0);
        spaceship.setMouseLocation(x, y);
        double dx = x - spaceship.x;
        double dy = y - spaceship.y - 10;
        spaceship.imageAngleRad = Math.atan2(dy, dx);
        if (spaceship.boosterFlag) {
            spaceship.dy = 4;
        } else {
            spaceship.dy = 3;
        }
        
        // If the game haven't started, allow mouse to hover over start menu
        if (!Main.animator.gameStart && !Main.gameData.gameOver) {
            if (x > Main.gamePanel.getWidth()/2 &&
                x < Main.gamePanel.getWidth()/2 + 100 &&
                y > Main.gamePanel.getHeight()/4 && 
                y < Main.gamePanel.getHeight()/4 + 40) {
                Main.gameData.startButton.hovered = true;
            } else {
                Main.gameData.startButton.hovered = false;
            }
        } else if (Main.gameData.gameOver) {
            if (x > Main.gamePanel.getWidth()/2 &&
                x < Main.gamePanel.getWidth()/2 + 100 &&
                y > Main.gamePanel.getHeight()/4 && 
                y < Main.gamePanel.getHeight()/4 + 40) {
                Main.gameData.startButton.hovered = true;
            } else {
                Main.gameData.startButton.hovered = false;
            }            
        }
        
    }

}
