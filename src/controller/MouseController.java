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
            Bullet m = new Bullet(
                spaceship.x + 64,
                spaceship.y + (29 / 2),
                px, py, // target location where the missile explodes
                Color.YELLOW);
            Main.gameData.friendFigures.add(m);
        } else if (SwingUtilities.isRightMouseButton(me)) {
            System.out.println("right click");
//            Missile mi = new Missile(
//                spaceship.x + 64,
//                spaceship.y + (29 / 2),
//                px, py);
            Main.gameData.friendFigures.add(new Missile(
                spaceship.x + 64,
                spaceship.y + (29 / 2),
                px, py));
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        Spaceship spaceship = (Spaceship) Main.gameData.friendFigures.get(0);
        spaceship.setMouseLocation(x, y);
        double dx = x - spaceship.x;
        double dy = y - spaceship.y;
        spaceship.imageAngleRad = Math.atan2(dy, dx);
    }

}
