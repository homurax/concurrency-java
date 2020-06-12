package com.homurax.synchronization.semaphore;

import com.homurax.synchronization.common.CommonTask;

import java.util.concurrent.Semaphore;

public class SemaphoreTask implements Runnable {

    private final Semaphore semaphore;

    public SemaphoreTask(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        try {
            semaphore.acquire();
            CommonTask.doTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
