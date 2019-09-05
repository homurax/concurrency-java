package com.homurax.chapter03.server.common.concurrent;

import com.homurax.chapter03.server.common.Command;
import com.homurax.chapter03.server.wdi.data.WDIDAO;

/**
 * Class that implements the concurrent version of the Query Command. The format of
 * this query is: q;codCountry;codIndicator;year where codCountry is the code of the country,
 * codIndicator is the code of the indicator and the year is an optional parameter with the year
 * you want to query
 *
 * @author author
 */
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
