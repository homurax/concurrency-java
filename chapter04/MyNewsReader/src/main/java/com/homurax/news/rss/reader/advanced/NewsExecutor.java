package com.homurax.news.rss.reader.advanced;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class NewsExecutor extends ScheduledThreadPoolExecutor {

    public NewsExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    @Override
    protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable, RunnableScheduledFuture<V> task) {
        return new ExecutorTask<>(runnable, null, task, this);
    }
}
