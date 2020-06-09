package com.homurax.objectFilter.concurrent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskManager {

    private final Set<RecursiveTask<?>> tasks;
    private final AtomicBoolean cancelled;

    public TaskManager() {
        this.tasks = ConcurrentHashMap.newKeySet();
        this.cancelled = new AtomicBoolean(false);
    }

    public void addTask(RecursiveTask<?> task) {
        tasks.add(task);
    }

    public void deleteTask(RecursiveTask<?> task) {
        tasks.remove(task);
    }

    public void cancelTasks(RecursiveTask<?> sourceTask) {
        if (cancelled.compareAndSet(false, true)) {
            for (RecursiveTask<?> task : tasks) {
                if (task != sourceTask) {
                    if (cancelled.get()) {
                        task.cancel(true);
                    } else {
                        tasks.add(task);
                    }
                }
            }
        }
    }

}
