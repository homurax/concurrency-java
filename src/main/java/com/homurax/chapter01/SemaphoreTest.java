package com.homurax.chapter01;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    // 多元复用
    public static void main(String[] args) {

        SemaphoreTest output = new SemaphoreTest();
        for (int i = 0; i < 5; i++) {
            new Thread(output::doSomething).start();
        }
    }

    private Semaphore semaphore = new Semaphore(6);

    private void doSomething() {
        try {
            semaphore.acquire(3);
            System.out.println(Thread.currentThread().getName() + " - start - " + LocalDateTime.now());
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " - end - " + LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(3);
        }
    }
}
