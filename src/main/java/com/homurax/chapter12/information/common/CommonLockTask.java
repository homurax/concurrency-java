package com.homurax.chapter12.information.common;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class CommonLockTask implements Runnable {

    private Lock lock;

    public CommonLockTask(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        for (int i = 0; i < 3; i++) {
            lock.lock();
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s - %s: Working %d seconds\n", LocalDateTime.now(), Thread.currentThread().getName(), duration);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

}
