package com.homurax.synchronization.countdown;

import com.homurax.synchronization.common.CommonTask;

import java.util.concurrent.CountDownLatch;

public class CountDownTask implements Runnable {

    private final CountDownLatch countDownLatch;

    public CountDownTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        CommonTask.doTask();
        countDownLatch.countDown();
    }
}
