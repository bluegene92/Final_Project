package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import model.Bullet;
import model.Missile;
import model.Spaceship;

public class MouseController extends MouseAdapter {
    
    public static int x;
    public static int y;

    volatile private boolean mouseDown = false;
    
    
    @Override
    public void mousePressed(MouseEvent me) {
        
        Spaceship spaceship = (Spaceship) Main.gameData.friendFigures.get(0);
        int px = x;
        int py = y;
        
        
        if (SwingUtilities.isLeftMouseButton(me)) {
            
            // If the game haven't start
            if (!Main.animator.gameStart) {
                if (px > Main.gamePanel.getWidth()/2 &&
                px < Main.gamePanel.getWidth()/2 + 100 &&
                py > Main.gamePanel.getHeight()/4 && 
                py < Main.gamePanel.getHeight()/4 + 40) {
                    Main.animator.gameStart = true;
                }
            }
            
            
            
            Bullet m = new Bullet(
                spaceship.x + 64,
                spaceship.y + (29 / 2),
                px, py, // target location where the missile explodes
                Color.YELLOW);
            
            // Shoot only if the game started
            if (Main.animator.gameStart) {
                Main.gameData.friendFigures.add(m);
            }
        } else if (SwingUtilities.isRightMouseButton(me)) {
            System.out.println("right click");
//            Missile mi = new Missile(
//                spaceship.x + 64,
//                spaceship.y + (29 / 2),
//                px, py);

            // Shoot only if the game started
            if (Main.animator.gameStart) {
                Main.gameData.friendFigures.add(new Missile(
                    spaceship.x + 64,
                    spaceship.y + (29 / 2),
                    px, py));
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
        
        
        // If the game haven't started, allow mouse to hover over start menu
        if (!Main.animator.gameStart) {
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
