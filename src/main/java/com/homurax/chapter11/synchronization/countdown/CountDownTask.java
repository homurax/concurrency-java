package com.homurax.chapter11.synchronization.countdown;

import com.homurax.chapter11.synchronization.common.CommonTask;

import java.util.concurrent.CountDownLatch;

public class CountDownTask implements Runnable {

    private CountDownLatch countDownLatch;

    public CountDownTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        CommonTask.doTask();
        countDownLatch.countDown();
    }
}
