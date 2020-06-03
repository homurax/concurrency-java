package com.homurax.simpleserver.common;

import com.homurax.simpleserver.concurrent.server.ConcurrentServer;

public class ConcurrentStopCommand extends Command {

    public ConcurrentStopCommand(String[] command) {
        super(command);
        setCacheable(false);
    }

    @Override
    public String execute() {
        ConcurrentServer.shutdown();
        return "Server stopped";
    }
}
