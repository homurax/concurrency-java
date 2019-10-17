package com.homurax.chapter11.structure.compare.nonblocking;

import com.homurax.chapter11.structure.compare.Item;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NonBlockingProducer implements Runnable {

    private ConcurrentLinkedQueue<Item> queue;

    public NonBlockingProducer(ConcurrentLinkedQueue<Item> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Producer start: " + Thread.currentThread().getName());
        for (int i = 0; i < 1000000; i++) {
            Item item = new Item(i, Thread.currentThread().getName(), new Date());
            queue.offer(item);
        }
        System.out.println("Producer end: " + Thread.currentThread().getName());
    }

}
