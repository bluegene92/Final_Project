package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ForceField extends GameFigure implements CollisionVistable {

    private BufferedImage image;
    private int width;
    private int height;
    Spaceship spaceship;
    private float collisionX;
    private float collisionY;
    public ForceField(float x, float y) {
        super(x, y);
        try {
            image = ImageIO.read(getClass().getResource("forcefield.png"));
            image = resize(image, image.getWidth()/3, image.getHeight()/3);
            width = image.getWidth();
            height = image.getHeight();
            collisionX = width * 0.8f;
            collisionY = height * 0.8f;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open forcefield.png");
            System.exit(-1);
        }
    }
    @Override
    public void render(Graphics2D g) {
        if (Main.gameData.manaBar.mana > 0) {
            g.drawImage(image, (int) x - width/6, (int) y - height/3 - 4, null);
        }
//        g.draw(new Rectangle2D.Float(x - 5, y - 27, collisionX, collisionY));
    }

    @Override
    public void update() {
        x = Main.gameData.spaceship.x;
        y = Main.gameData.spaceship.y;
    }
    
    public void link(Spaceship s) {
        spaceship = s;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        if (Main.gameData.spaceship.forceFieldFlag && Main.gameData.manaBar.mana > 0) {
            return new Rectangle2D.Float(x - 5, y - 27, collisionX, collisionY);
        }
        return new Rectangle2D.Float(x, y - 27, 1, 1);
    }

    @Override
    public void hit(GameFigure gameFigure) {}
    
    @Override
    public void accept(CollisionVisitor v) {
        v.visit(this);
    }

}
