package com.homurax.chapter03.server.common;

public class ErrorCommand extends Command {

    public ErrorCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        return "Unknown command: " + command[0];
    }
}
