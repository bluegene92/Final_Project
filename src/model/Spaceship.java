package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Spaceship extends GameFigure implements CollisionVistable {

    private final int WIDTH = 64;
    private final int HEIGHT = 29;
    private final int UNIT_TRAVEL = 2; // per frame
    private float mouseX;
    private float mouseY;
    public int dy = 3;
    private BufferedImage image;
    private int frameNumber = 0;
    public static double imageAngleRad = 0;
    public boolean boosterFlag = false;
    public int missileCharge;
    public int health = 5;
    public HealthBar healthBar;
    private int direction = 1; // +1: to the right; -1 to the left
    public boolean forceFieldFlag = false;
    public Timer manaTimer;
    public Timer manaRecharge;
    public boolean isHit = false;
    
    public Spaceship(float x, float y) {
        super(x, y); // origin: upper-left corner
        missileCharge = 5;
        setState(new ActiveState());
        healthBar = new HealthBar(50, 50);
        try {
            image = ImageIO.read(getClass().getResource("f1.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open spaceship.png");
            System.exit(-1);
        }
        
        manaTimer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.gameData.manaBar.mana > 0) {
                    Main.gameData.manaBar.mana--;
                }
            }
        });
        
        manaRecharge = new Timer(500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Main.gameData.manaBar.mana <= 50) {
                    Main.gameData.manaBar.mana++;
                }
            }
        });
    }

    @Override
    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int cx = 64/ 2;
        int cy = 29 /2;
        AffineTransform oldAT = g.getTransform();
        g.translate(cx + super.x, cy + super.y);
        g.rotate(imageAngleRad);
        g.translate(-cx, -cy);
        g.drawImage(image, 0, 0, null);
        g.setTransform(oldAT);
        healthBar.render(g);
    }

    
    public void setMouseLocation(float x, float y) {
        this.mouseX = x;
        this.mouseY = y;
    }
    
    public void turnOnForceField() {
        if (!forceFieldFlag) {
            float x = Main.gameData.spaceship.x;
            float y = Main.gameData.spaceship.y;
            Main.gameData.friendFigures.add(new ForceField(x, y));
            manaTimer.start();
            manaRecharge.stop();
            forceFieldFlag = true;
        }
        
    }
    
    public void turnOffForceField() {
        if (forceFieldFlag) {
            for (GameFigure ff : Main.gameData.friendFigures) {
                if (ff instanceof ForceField) {
                    Main.gameData.removeFigures.add(ff);
                }
            }
            manaTimer.stop();
            manaRecharge.start();
            forceFieldFlag = false;
        }

    }
    
    @Override
    public void update() {
        if (super.y < mouseY - 10) {
            super.y+= dy;
        } else if (super.y >= mouseY) {
            super.y-= dy;
        }
        
        
        if (boosterFlag && super.x < Main.WIN_WIDTH / 4) {
            super.x += 1;
        } else if (super.x > 30) {
            super.x -= 1;
        }
    }
    
    public void boosterOn() {
        boosterFlag = true;
    }
 
    public void boosterOff() {
        boosterFlag = false;
    }
    
    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(super.x, (int) super.y, WIDTH, HEIGHT);
    }
    
    @Override
    public void hit(GameFigure gameFigure) {
        if (!isHit) {
            loseHealth();
            isHit = true;
        }
    }
    
    public void loseHealth() {
        if (healthBar.index >= 0 && healthBar.index < 5) {
            healthBar.index++;
        }
        
        if (healthBar.index >= 5) {
            Main.animator.gameStart = false;
        }
        System.out.println(healthBar.index);
    }

    @Override
    public void accept(CollisionVisitor v) {
        v.visit(this);
    }
}
