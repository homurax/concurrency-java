package com.homurax.chapter03.server.common;

import com.homurax.chapter03.server.wdi.data.WDIDAO;

/**
 * Class that implements the serial version of the Report command.
 * Report: The format of this query is: r:codIndicator where codIndicator
 * is the code of the indicator you want to report
 *
 * @author author
 */
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
