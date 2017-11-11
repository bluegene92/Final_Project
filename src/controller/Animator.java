package controller;

import java.util.concurrent.TimeUnit;
import model.GameFigureState;

public class Animator implements Runnable {

    public boolean running = true;
    private final int FRAMES_PER_SECOND = 60;
    public boolean gameStart = false;
    
    @Override
    public void run() {

        while (running) {
            long startTime = System.currentTimeMillis();
            
            processCollisions();
            if (gameStart) {
                Main.gameData.update();
            }
            Main.gamePanel.gameRender();
            Main.gamePanel.printScreen();

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);

            if (sleepTime > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {

                }
            }
        }
        System.exit(0);
    }
    
    private void processCollisions() {
        // detect collisions between friendFigure and enemyFigures
        // if detected, mark it as STATE_DONE, so that
        // they can be removed at update() method
        
        
        
        for (int i = 0; i < Main.gameData.friendFigures.size(); ++i) {
            for (int j = 0; j < Main.gameData.enemyFigures.size(); ++j) {
                if (Main.gameData.friendFigures.get(i)
                        .getCollisionBox()
                        .intersects(Main.gameData.enemyFigures.get(j).getCollisionBox())) {
                        
                        
                        /**
                         * Have the UFO fall out of the frame, or
                         * have bomb exploded in update()
                         */
                        Main.gameData.enemyFigures.get(j).state = GameFigureState.STATE_DONE;
       
                        
                }
            }// End inner for
        } // End outer for
        
    } // End processCollisions()


}
