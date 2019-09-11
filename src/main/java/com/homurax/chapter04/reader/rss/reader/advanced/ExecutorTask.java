package com.homurax.chapter04.reader.rss.reader.advanced;

import com.homurax.chapter04.reader.rss.reader.basic.NewsTask;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {

    private RunnableScheduledFuture<V> task;

    private NewsExecutor executor;

    private long startDate;

    private String name;


    public ExecutorTask(Runnable runnable,
                        V result,
                        RunnableScheduledFuture<V> task,
                        NewsExecutor executor) {

        super(runnable, result);
        this.task = task;
        this.executor = executor;
        this.name = ((NewsTask) runnable).getName();
        this.startDate = System.currentTimeMillis();
    }


    @Override
    public long getDelay(TimeUnit unit) {
        long delay;
        if (!isPeriodic()) {
            delay = task.getDelay(unit);
        } else {
            if (startDate == 0) {
                delay = task.getDelay(unit);
            } else {
                delay = startDate - System.currentTimeMillis();
                delay = unit.convert(delay, TimeUnit.MILLISECONDS);
            }
        }
        return delay;
    }


    @Override
    public int compareTo(Delayed object) {
        return Long.compare(this.getStartDate(), ((ExecutorTask<V>) object).getStartDate());
    }


    @Override
    public boolean isPeriodic() {
        return task.isPeriodic();
    }


    @Override
    public void run() {
        if (isPeriodic() && (!executor.isShutdown())) {
            super.runAndReset();
            startDate = System.currentTimeMillis() + Timer.getPeriod();
            executor.getQueue().add(this);
            System.out.println("Start Date: " + Instant.ofEpochMilli(startDate).atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }


    public String getName() {
        return name;
    }

    public long getStartDate() {
        return startDate;
    }
}
