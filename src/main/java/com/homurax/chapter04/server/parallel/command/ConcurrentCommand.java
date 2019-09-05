package com.homurax.chapter04.server.parallel.command;

import com.homurax.chapter03.server.common.Command;
import com.homurax.chapter03.server.parallel.cache.ParallelCache;
import com.homurax.chapter03.server.parallel.log.Logger;
import com.homurax.chapter03.server.parallel.server.ConcurrentServer;
import lombok.Data;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

@Data
public abstract class ConcurrentCommand extends Command implements Comparable<ConcurrentCommand>, Runnable {

    private String username;
    private byte priority;
    private Socket socket;

    public ConcurrentCommand(Socket socket, String[] command) {
        super(command);
        this.username = command[1];
        this.priority = Byte.parseByte(command[2]);
        this.socket = socket;
    }

    @Override
    public abstract String execute();

    @Override
    public void run() {

        String message = "Running a Task: Username: " + username + "; Priority: " + priority;
        Logger.sendMessage(message);

        String result = execute();

        ParallelCache cache = ConcurrentServer.getCache();

        if (isCacheable()) {
            cache.put(String.join(";", command), result);
        }

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);
    }


    @Override
    public int compareTo(ConcurrentCommand o) {
        return Byte.compare(o.getPriority(), this.getPriority());
    }
}
