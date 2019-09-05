package com.homurax.chapter04.server.parallel.command;

import com.homurax.chapter03.server.parallel.log.Logger;
import com.homurax.chapter04.server.parallel.server.ConcurrentServer;

import java.net.Socket;

public class ConcurrentCancelCommand extends ConcurrentCommand {

    public ConcurrentCancelCommand(Socket socket, String[] command) {
        super(socket, command);
        setCacheable(false);
    }

    @Override
    public String execute() {
        ConcurrentServer.cancelTasks(getUsername());
        String message = "Tasks of user " + getUsername() + " has been cancelled.";
        Logger.sendMessage(message);
        return message;
    }

}
