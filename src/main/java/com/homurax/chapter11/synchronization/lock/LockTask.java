package com.homurax.chapter11.synchronization.lock;

import com.homurax.chapter11.synchronization.common.CommonTask;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class LockTask implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    private String name;

    public LockTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("Task: " + name + "; Date: " + new Date() + ": Running the task");
            CommonTask.doTask();
            System.out.println("Task: " + name + "; Date: " + new Date() + ": The execution has finished");
        } finally {
            lock.unlock();
        }
    }

}
