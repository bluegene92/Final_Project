/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Score {

    int score = 0;
    
    public Score() {
        
    }
    
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 12));
        g.drawString("Score: " + score, 50, 80);
    }
}
