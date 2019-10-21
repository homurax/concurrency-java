package com.homurax.chapter12.information.executor;

import com.homurax.chapter12.information.common.CommonTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainExecutor {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            executor.execute(new CommonTask());
        }

        for (int i = 0; i < 10; i++) {
			printExecutor(executor);
			try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

		printExecutor(executor);
	}

	private static void printExecutor(ThreadPoolExecutor executor) {
		System.out.println("Active Count: " + executor.getActiveCount());
		System.out.println("Completed Task Count: " + executor.getCompletedTaskCount());
		System.out.println("Core Pool Size: " + executor.getCorePoolSize());
		System.out.println("Largest Pool Size: " + executor.getLargestPoolSize());
		System.out.println("Maximum Pool Size: " + executor.getMaximumPoolSize());
		System.out.println("Pool Size: " + executor.getPoolSize());
		System.out.println("Task Count: " + executor.getTaskCount());
		System.out.println("Terminated: " + executor.isTerminated());
		System.out.println("Is Terminating: " + executor.isTerminating());
		System.out.println("*******************************************");
	}

}
