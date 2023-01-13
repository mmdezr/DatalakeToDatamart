package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    DatalakeReader datalakeReader = new DatalakeReader();
                    datalakeReader.processEvents();
                } catch (IOException | SQLException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 60 * 60 * 1000);
    }
}