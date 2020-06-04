package com.homurax.advancedServer.serial.command;

import com.homurax.advancedServer.common.Command;

public class ErrorCommand extends Command {

    public ErrorCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        return "Unknown command: " + command[0];
    }

}
