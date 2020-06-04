package com.homurax.advancedServer.serial.command;

import com.homurax.advancedServer.common.Command;

public class StopCommand extends Command {

    public StopCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        return "Server stopped";
    }

}
