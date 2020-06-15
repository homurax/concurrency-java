package com.homurax.test.common;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class CommonLockTask implements Runnable {

    private final Lock lock;

    public CommonLockTask(Lock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        for (int i = 0; i < 3; i++) {
            lock.lock();
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s - %s: Working %d seconds\n", new Date(), Thread.currentThread().getName(), duration);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

}
