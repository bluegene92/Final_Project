package view;
import controller.KeyController;
import controller.Main;
import controller.MouseController;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {

    public static JButton addButton;
    public static JButton quitButton;
    public static JButton addUFOButton;
    public static MouseController mouseController;

    public MainWindow() {

        Container c = getContentPane();

        c.add(Main.gamePanel, "Center");
        
        
        mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);
        Main.gamePanel.addMouseMotionListener(mouseController);

        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
    }

}
