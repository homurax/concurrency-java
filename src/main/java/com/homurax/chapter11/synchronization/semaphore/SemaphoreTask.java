package com.homurax.chapter11.synchronization.semaphore;

import com.homurax.chapter11.synchronization.common.CommonTask;

import java.util.concurrent.Semaphore;

public class SemaphoreTask implements Runnable {

    private Semaphore semaphore;

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
