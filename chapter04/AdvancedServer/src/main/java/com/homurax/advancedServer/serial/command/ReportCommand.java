package com.homurax.advancedServer.serial.command;

import com.homurax.advancedServer.common.Command;
import com.homurax.advancedServer.wdi.data.WDIDAO;

public class ReportCommand extends Command {

    public ReportCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[1]);
    }

}
