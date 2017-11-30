package model;
import controller.Main;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Boss extends GameFigure implements CollisionVistable {
    private BufferedImage image;
    public int width;
    public int height;
    public BossHealthBar bossHealthBar = new BossHealthBar(Main.WIN_WIDTH - 220, 20);
    private float collisionX;
    public boolean isHit = false;
    private Timer timer;
    
    public Boss(float x, float y) {
        super(x, y);
        myState = new BossActiveState();
        
        try {
            image = ImageIO.read(getClass().getResource("boss.png"));
            width = image.getWidth();
            height = image.getHeight();
            image = resize(image, width/6, height/6);
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open boss.png");
            System.exit(-1);            
        }
        collisionX = (float) (width * 0.6);
    }
    
    ActionListener actionFire = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            fire();
        }
    };
    
    public void fire() {
        Main.gameData.enemyFigures.add(
                new FireBall(x + width/2, y + height/2, 
                        Main.gameData.spaceship.x, 
                        Main.gameData.spaceship.y));
        Main.gameData.enemyFigures.add(
        new FireBall(x + width/2, y + height/2, 
                Main.gameData.spaceship.x, 
                Main.gameData.spaceship.y - 220));

        Main.gameData.enemyFigures.add(
        new FireBall(x + width/2, y + height/2, 
                Main.gameData.spaceship.x, 
                Main.gameData.spaceship.y + 220));        
    }
    
    @Override
    public void setState(State s) {
        myState = s;
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(image, (int) x, (int) super.y, null);
//        g.draw(new Rectangle2D.Float(x + collisionX/3, y, collisionX, height));
        bossHealthBar.render(g);
        
    }

    @Override
    public void update() {
        myState.doAction(this);
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Float(x + collisionX/3, y, collisionX, height);
    }

    @Override
    public void hit(GameFigure gameFigure) {
        if (!isHit) {
            if (gameFigure instanceof Bullet) {
                bossHealthBar.health--;
                isHit = true;
            } else if (gameFigure instanceof Missile) {
                bossHealthBar.health -= 5;
                isHit = true;
            }
        }
    }

    @Override
    public void accept(CollisionVisitor v) {
        v.visit(this);
    }
}
