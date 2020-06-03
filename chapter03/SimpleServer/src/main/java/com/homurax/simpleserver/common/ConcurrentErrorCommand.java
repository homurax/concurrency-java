package com.homurax.simpleserver.common;


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
