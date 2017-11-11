/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class StartButton {

    private String text = "Start";
    public boolean hovered = false;
    
    public void draw(Graphics2D g) {
        g.setFont(new Font("Courier New", Font.BOLD, 24));

        
        if (!Main.animator.gameStart) {
            if (!hovered) {
                g.setColor(Color.WHITE);
                g.draw(new Rectangle2D.Float(Main.gamePanel.getWidth()/2, 
                    Main.gamePanel.getHeight()/4, 100, 40));
                g.drawString(text, Main.gamePanel.getWidth()/2 + 15, 
                    Main.gamePanel.getHeight()/4 + 30);
            } else {
                g.fill(new Rectangle2D.Float(Main.gamePanel.getWidth()/2, 
                    Main.gamePanel.getHeight()/4, 100, 40));
                g.setColor(Color.BLACK);
                g.drawString(text, Main.gamePanel.getWidth()/2 + 15, 
                    Main.gamePanel.getHeight()/4 + 30);

            }
        }

    }
    
    public void update() {
        
    }
}
