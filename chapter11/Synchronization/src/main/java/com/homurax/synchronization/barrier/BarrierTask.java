package com.homurax.synchronization.barrier;

import com.homurax.synchronization.common.CommonTask;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierTask implements Runnable {

    private final CyclicBarrier barrier;

    public BarrierTask(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": Phase 1");
        CommonTask.doTask();
        try {
            int awaitNum = barrier.await();
            System.out.println(Thread.currentThread().getName() + " - awaitNum : " + awaitNum);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ": Phase 2");
    }
}
