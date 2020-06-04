package com.homurax.advancedServer.serial.command;

import com.homurax.advancedServer.common.Command;
import com.homurax.advancedServer.wdi.data.WDIDAO;

public class QueryCommand extends Command {

    public QueryCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {

        WDIDAO dao = WDIDAO.getDAO();
        if (command.length == 3) {
            return dao.query(command[1], command[2]);
        } else if (command.length == 4) {
            try {
                return dao.query(command[1], command[2], Short.parseShort(command[3]));
            } catch (Exception e) {
                return "ERROR;Bad Command";
            }
        } else {
            return "ERROR;Bad Command";
        }
    }

}
