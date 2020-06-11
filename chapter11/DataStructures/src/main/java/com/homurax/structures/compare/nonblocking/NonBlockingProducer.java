package com.homurax.structures.compare.nonblocking;

import com.homurax.structures.compare.Item;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NonBlockingProducer implements Runnable {

    private final ConcurrentLinkedQueue<Item> queue;

    public NonBlockingProducer(ConcurrentLinkedQueue<Item> queue) {
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
            queue.offer(item);
        }
        System.out.println("Producer end: " + Thread.currentThread().getName());
    }

}
