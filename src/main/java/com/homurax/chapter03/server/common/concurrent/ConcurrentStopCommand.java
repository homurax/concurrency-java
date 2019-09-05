package com.homurax.chapter03.server.common.concurrent;

import com.homurax.chapter03.server.common.Command;
import com.homurax.chapter03.server.parallel.server.ConcurrentServer;

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
