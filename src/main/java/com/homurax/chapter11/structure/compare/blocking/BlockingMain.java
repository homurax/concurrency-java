package com.homurax.chapter11.structure.compare.blocking;

import com.homurax.chapter11.structure.compare.Item;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockingMain {

    public static void main(String[] args) {

        int numTasks = 4;

        LinkedBlockingQueue<Item> queue = new LinkedBlockingQueue<>();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        long start = System.currentTimeMillis();
        for (int i = 0; i < numTasks; i++) {
            BlockingProducer producer = new BlockingProducer(queue);
            executor.execute(producer);
        }

        for (int i = 0; i < numTasks; i++) {
            BlockingConsumer consumer = new BlockingConsumer(queue);
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
