package com.homurax.chapter12.information.fork;

import com.homurax.chapter12.information.common.CommonForkTask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class MainFork {

    public static void main(String[] args) {

        CommonForkTask task = new CommonForkTask(0, 10000);
        ForkJoinPool pool = new ForkJoinPool(2);
        pool.execute(task);

        while (!task.isDone()) {
            System.out.println("Parallelism: " + pool.getParallelism());
            System.out.println("Pool Size: " + pool.getPoolSize());
            System.out.println("Active Thread Count: " + pool.getActiveThreadCount());
            System.out.println("Running Thread Count: " + pool.getRunningThreadCount());
            System.out.println("Queued Submission: " + pool.getQueuedSubmissionCount());
            System.out.println("Queued Tasks: " + pool.getQueuedTaskCount());
            System.out.println("Queued Submissions: " + pool.hasQueuedSubmissions());
            System.out.println("Steal Count: " + pool.getStealCount());
            System.out.println("Terminated : " + pool.isTerminated());
            System.out.println("**********************");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
