package com.homurax.advancedServer.concurrent.executor;

import com.homurax.advancedServer.concurrent.command.ConcurrentCommand;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedTaskController implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable task, ThreadPoolExecutor executor) {

        ConcurrentCommand command = (ConcurrentCommand) task;

        try (Socket clientSocket = command.getSocket();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message = "The server is shutting down. Your request can not be served."
                    + " Shuting Down: " + executor.isShutdown()
                    + ". Terminated: " + executor.isTerminated()
                    + ". Terminating: " + executor.isTerminating();
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
