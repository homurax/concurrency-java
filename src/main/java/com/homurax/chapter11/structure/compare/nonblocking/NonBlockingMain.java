package com.homurax.chapter11.structure.compare.nonblocking;

import com.homurax.chapter11.structure.compare.Item;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NonBlockingMain {

    public static void main(String[] args) {

        int numTasks = 4;

        ConcurrentLinkedQueue<Item> queue = new ConcurrentLinkedQueue<>();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

		long start = System.currentTimeMillis();
        for (int i = 0; i < numTasks; i++) {
            NonBlockingProducer producer = new NonBlockingProducer(queue);
            executor.execute(producer);
        }

        for (int i = 0; i < numTasks; i++) {
            NonBlockingConsumer consumer = new NonBlockingConsumer(queue);
            executor.execute(consumer);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		long end = System.currentTimeMillis();

        System.out.println("Execution Time: " + (end - start));
    }

}
