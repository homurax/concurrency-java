package com.homurax.chapter04.server.parallel.executor;

import com.homurax.chapter03.server.parallel.log.Logger;
import com.homurax.chapter04.server.parallel.command.ConcurrentCommand;
import com.homurax.chapter04.server.parallel.server.ConcurrentServer;

import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerExecutor extends ThreadPoolExecutor {

    private ConcurrentHashMap<Runnable, Long> startTimes;

    private ConcurrentHashMap<String, ExecutorStatistics> executionStatistics;

    private static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static int MAXIMUM_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private static long KEEP_ALIVE_TIME = 10;

    private static RejectedTaskController REJECTED_TASK_CONTROLLER = new RejectedTaskController();


    public ServerExecutor() {
        super(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new PriorityBlockingQueue<>(), REJECTED_TASK_CONTROLLER);
        this.startTimes = new ConcurrentHashMap<>();
        this.executionStatistics = new ConcurrentHashMap<>();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTimes.put(r, System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        ServerTask<?> task = (ServerTask<?>) r;
        ConcurrentCommand command = task.getCommand();

        if (t == null) {
            if (!task.isCancelled()) {
                Long startDate = startTimes.remove(r);
                Long endDate = System.currentTimeMillis();
                long executionTime = endDate - startDate;
                ExecutorStatistics statistics = executionStatistics.computeIfAbsent(command.getUsername(), n -> new ExecutorStatistics());
                statistics.addExecutionTime(executionTime);
                statistics.addTask();
                ConcurrentServer.finishTask(command.getUsername(), command);
            } else {
                String message = "The task " + command.hashCode()
                        + " of user " + command.getUsername()
                        + " has been cancelled.";
                Logger.sendMessage(message);
            }

        } else {
            String message = "The exception "
                    + t.getMessage()
                    + " has been thrown.";
            Logger.sendMessage(message);
        }
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new ServerTask<>((ConcurrentCommand) runnable);
    }

    public void writeStatistics() {
        for (Entry<String, ExecutorStatistics> entry : executionStatistics.entrySet()) {
            String user = entry.getKey();
            ExecutorStatistics stats = entry.getValue();
            Logger.sendMessage(user + ":" + stats);
        }
    }

}
