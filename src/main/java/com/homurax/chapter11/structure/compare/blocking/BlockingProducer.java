package com.homurax.chapter11.structure.compare.blocking;

import com.homurax.chapter11.structure.compare.Item;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingProducer implements Runnable {

    private LinkedBlockingQueue<Item> queue;

    public BlockingProducer(LinkedBlockingQueue<Item> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Producer start: " + Thread.currentThread().getName());
        for (int i = 0; i < 1000000; i++) {
            Item item = new Item(i, Thread.currentThread().getName(), new Date());
            try {
                queue.put(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Producer end: " + Thread.currentThread().getName());
    }

}
