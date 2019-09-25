package com.homurax.chapter07.filter.concurrent;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskManager {

    private Set<RecursiveTask> tasks;
    private AtomicBoolean cancelled;

    public TaskManager() {
        this.tasks = ConcurrentHashMap.newKeySet();
		this.cancelled = new AtomicBoolean(false);
    }

    public void addTask(RecursiveTask task) {
		this.tasks.add(task);
    }

    public void cancelTasks(RecursiveTask sourceTask) {

        if (cancelled.compareAndSet(false, true)) {
            for (RecursiveTask task : tasks) {
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

    public void deleteTask(RecursiveTask task) {
		this.tasks.remove(task);
    }

}
