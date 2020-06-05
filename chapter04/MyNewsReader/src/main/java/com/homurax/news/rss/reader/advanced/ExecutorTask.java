package com.homurax.news.rss.reader.advanced;

import com.homurax.news.rss.reader.basic.NewsTask;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorTask<V> extends FutureTask<V> implements RunnableScheduledFuture<V> {

    private final RunnableScheduledFuture<V> task;

    private final NewsExecutor executor;

    private long startDate;

    private final String name;

    public ExecutorTask(Runnable runnable, V result,
                        RunnableScheduledFuture<V> task,
                        NewsExecutor executor) {

        super(runnable, result);
        this.task = task;
        this.executor = executor;
        this.name = ((NewsTask) runnable).getName();
        this.startDate = new Date().getTime();
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
                Date now = new Date();
                delay = startDate - now.getTime();
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
            Date now = new Date();
            startDate = now.getTime() + Timer.getPeriod();
            executor.getQueue().add(this);
            System.out.println("Start Date: " + new Date(startDate));
        }
    }

    public String getName() {
        return name;
    }

    public long getStartDate() {
        return startDate;
    }

}
