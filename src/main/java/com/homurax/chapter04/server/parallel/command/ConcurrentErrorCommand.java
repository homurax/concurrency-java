package com.homurax.chapter04.server.parallel.command;

import java.net.Socket;

public class ConcurrentErrorCommand extends ConcurrentCommand {

    public ConcurrentErrorCommand(Socket socket, String[] command) {
        super(socket, command);
        setCacheable(false);
    }

    @Override
    public String execute() {
        return "Unknown command: " + command[0];
    }
}
