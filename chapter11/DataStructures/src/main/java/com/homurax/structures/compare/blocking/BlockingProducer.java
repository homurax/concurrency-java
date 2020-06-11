package com.homurax.structures.compare.blocking;

import com.homurax.structures.compare.Item;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingProducer implements Runnable {

    private final LinkedBlockingQueue<Item> queue;

    public BlockingProducer(LinkedBlockingQueue<Item> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Producer start: " + Thread.currentThread().getName());
        for (int i = 0; i < 1000000; i++) {
            Item item = new Item();
            item.setDate(new Date());
            item.setId(i);
            item.setThreadName(Thread.currentThread().getName());
            try {
                queue.put(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Producer end: " + Thread.currentThread().getName());
    }

}
