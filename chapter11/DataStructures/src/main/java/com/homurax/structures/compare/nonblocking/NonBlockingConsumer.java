package com.homurax.structures.compare.nonblocking;

import com.homurax.structures.compare.Item;

import java.util.concurrent.ConcurrentLinkedQueue;

public class NonBlockingConsumer implements Runnable {

    private final ConcurrentLinkedQueue<Item> queue;

    public NonBlockingConsumer(ConcurrentLinkedQueue<Item> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Consumer start: " + Thread.currentThread().getName());
        for (int i = 0; i < 1000000; i++) {
            Item item = queue.poll();
            System.out.println(item.getThreadName() + ":" + item.getId() + ":" + item.getDate());
        }
        System.out.println("Consumer end: " + Thread.currentThread().getName());
    }

}
