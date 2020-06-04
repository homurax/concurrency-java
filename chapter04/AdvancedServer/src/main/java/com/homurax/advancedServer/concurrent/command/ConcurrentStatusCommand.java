package com.homurax.advancedServer.concurrent.command;

import com.homurax.advancedServer.concurrent.server.ConcurrentServer;
import com.homurax.advancedServer.parallel.log.Logger;

import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentStatusCommand extends ConcurrentCommand {

    private final ThreadPoolExecutor executor;

    public ConcurrentStatusCommand(ThreadPoolExecutor executor, Socket socket, String[] command) {
        super(socket, command);
        this.executor = executor;
        setCacheable(false);
    }


    @Override
    public String execute() {

        StringBuilder sb = new StringBuilder();

        sb.append("Server Status;");
        sb.append("Actived Threads: ");
        sb.append(executor.getActiveCount());
        sb.append(";");
        sb.append("Maximum Pool Size: ");
        sb.append(executor.getMaximumPoolSize());
        sb.append(";");
        sb.append("Core Pool Size: ");
        sb.append(executor.getCorePoolSize());
        sb.append(";");
        sb.append("Pool Size: ");
        sb.append(executor.getPoolSize());
        sb.append(";");
        sb.append("Largest Pool Size: ");
        sb.append(executor.getLargestPoolSize());
        sb.append(";");
        sb.append("Completed Task Count: ");
        sb.append(executor.getCompletedTaskCount());
        sb.append(";");
        sb.append("Task Count: ");
        sb.append(executor.getTaskCount());
        sb.append(";");
        sb.append("Queue Size: ");
        sb.append(executor.getQueue().size());
        sb.append(";");
        sb.append("Cache Size: ");
        sb.append(String.valueOf(ConcurrentServer.getCache().getItemNumber()));
        sb.append(";");
        Logger.sendMessage(sb.toString());
        return sb.toString();
    }

}
