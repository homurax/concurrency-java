package com.homurax.synchronization.lock;

import com.homurax.synchronization.common.CommonTask;

import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class LockTask implements Runnable {

    private static final ReentrantLock lock = new ReentrantLock();
    private final String name;

    public LockTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("Task: " + name + "; Date: " + LocalDateTime.now() + ": Running the task");
            CommonTask.doTask();
            System.out.println("Task: " + name + "; Date: " + LocalDateTime.now() + ": The execution has finished");
            System.out.println("--------------------------");
        } finally {
            lock.unlock();
        }
    }

}
