package com.homurax.simpleserver.common;

import com.homurax.simpleserver.wdi.data.WDIDAO;

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
