package com.homurax.advancedServer.concurrent.command;

import com.homurax.advancedServer.common.Command;
import com.homurax.advancedServer.concurrent.server.ConcurrentServer;
import com.homurax.advancedServer.parallel.cache.ParallelCache;
import com.homurax.advancedServer.parallel.log.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ConcurrentCommand extends Command implements Comparable<ConcurrentCommand>, Runnable {

    private String username;

    private byte priority;

    private Socket socket;

    public ConcurrentCommand(Socket socket, String[] command) {
        super(command);
        username = command[1];
        priority = Byte.parseByte(command[2]);
        this.socket = socket;
    }


    @Override
    public abstract String execute();

    @Override
    public void run() {

        String message = "Running a Task: Username: " + username + "; Priority: " + priority;

        Logger.sendMessage(message);

        String ret = execute();

        ParallelCache cache = ConcurrentServer.getCache();

        if (isCacheable()) {
            cache.put(String.join(";", command), ret);
        }
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {

            out.println(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ret);
    }


    @Override
    public int compareTo(ConcurrentCommand o) {
        return Byte.compare(o.getPriority(), this.getPriority());
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public byte getPriority() {
        return priority;
    }


    public void setPriority(byte priority) {
        this.priority = priority;
    }


    public Socket getSocket() {
        return socket;
    }


    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
