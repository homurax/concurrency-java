package com.homurax.chapter03.server.common.concurrent;

import com.homurax.chapter03.server.common.Command;
import com.homurax.chapter03.server.wdi.data.WDIDAO;

public class ConcurrentReportCommand extends Command {

    public ConcurrentReportCommand(String[] command) {
        super(command);
    }

    @Override
    public String execute() {

        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[1]);
    }

}
