package com.homurax.simpleserver.parallel.log;

import java.util.concurrent.TimeUnit;

public class LogTask implements Runnable {

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.SECONDS.sleep(10);
                Logger.writeLogs();
            }
        } catch (InterruptedException e) {
        }
        Logger.writeLogs();
    }

}
