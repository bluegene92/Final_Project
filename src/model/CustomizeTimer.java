package model;

import controller.Main;
import java.util.Timer;
import java.util.TimerTask;

public class CustomizeTimer {

    public CustomizeTimer() {
        enableTimer();
    }
    
    public void enableTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                
            }
        }, 0, 1000);
    }    
}
