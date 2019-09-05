package com.homurax.chapter04.server.parallel.server;

import com.homurax.chapter03.server.parallel.cache.ParallelCache;
import com.homurax.chapter03.server.parallel.log.Logger;
import com.homurax.chapter04.server.parallel.command.*;
import com.homurax.chapter04.server.parallel.executor.ServerExecutor;
import com.homurax.chapter04.server.parallel.executor.ServerTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RequestTask implements Runnable {

    private LinkedBlockingQueue<Socket> pendingConnections;

    private ServerExecutor executor = new ServerExecutor();

    private ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController;


    public RequestTask(LinkedBlockingQueue<Socket> pendingConnections,
                       ConcurrentMap<String, ConcurrentMap<ConcurrentCommand, ServerTask<?>>> taskController) {
        this.pendingConnections = pendingConnections;
        this.taskController = taskController;
    }

    @Override
    public void run() {

        try {
            while (!Thread.currentThread().interrupted()) {
                try {
                    Socket clientSocket = pendingConnections.take();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String line = in.readLine();

                    Logger.sendMessage(line);

                    ConcurrentCommand command;

                    ParallelCache cache = ConcurrentServer.getCache();
                    String result = cache.get(line);
                    if (result == null) {
                        String[] commandData = line.split(";");
                        System.out.println("Command: " + commandData[0]);
                        switch (commandData[0]) {
                            case "q":
                                System.out.println("Query");
                                command = new ConcurrentQueryCommand(clientSocket, commandData);
                                break;
                            case "r":
                                System.out.println("Report");
                                command = new ConcurrentReportCommand(clientSocket, commandData);
                                break;
                            case "s":
                                System.out.println("Status");
                                command = new ConcurrentStatusCommand(executor, clientSocket, commandData);
                                break;
                            case "z":
                                System.out.println("Stop");
                                command = new ConcurrentStopCommand(clientSocket, commandData);
                                break;
                            case "c":
                                System.out.println("Cancel");
                                command = new ConcurrentCancelCommand(clientSocket, commandData);
                                break;
                            default:
                                System.out.println("Error");
                                command = new ConcurrentErrorCommand(clientSocket, commandData);
                                break;
                        }

                        ServerTask<?> controller = (ServerTask<?>) executor.submit(command);
                        storeController(command.getUsername(), controller, command);
                    } else {
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        out.println(result);
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            // No Action Required
        }
    }

    private void storeController(String userName, ServerTask<?> controller, ConcurrentCommand command) {
        taskController.computeIfAbsent(userName, k -> new ConcurrentHashMap<>()).put(command, controller);
    }

    public void shutdown() {
        String message = "Request Task: "
                + pendingConnections.size()
                + " pending connections.";
        Logger.sendMessage(message);
        executor.shutdown();
    }

    public void terminate() {
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
            executor.writeStatistics();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ServerExecutor getExecutor() {
        return executor;
    }
}
