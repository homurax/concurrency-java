package com.homurax.chapter12.information.common;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class CommonTask implements Runnable {

    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        System.out.printf("%s - %s: Working %d seconds\n", LocalDateTime.now(), Thread.currentThread().getName(), duration);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
