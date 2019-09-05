package com.homurax.chapter03.server.common.concurrent;

import com.homurax.chapter03.server.common.Command;

/**
 * Concurrent version of the ErrorCommand. It's executed when an unknown command arrives
 */
public class ConcurrentErrorCommand extends Command {

    public ConcurrentErrorCommand(String[] command) {
        super(command);
        setCacheable(false);
    }

    @Override
    public String execute() {
        return "Unknown command: " + command[0];
    }

}
