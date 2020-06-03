package com.homurax.simpleserver.common;

import com.homurax.simpleserver.wdi.data.WDIDAO;

public class ConcurrentQueryCommand extends Command {

    public ConcurrentQueryCommand(String[] command) {
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
