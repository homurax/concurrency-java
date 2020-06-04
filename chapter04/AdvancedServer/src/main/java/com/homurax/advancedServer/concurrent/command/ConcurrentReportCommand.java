package com.homurax.advancedServer.concurrent.command;

import com.homurax.advancedServer.wdi.data.WDIDAO;

import java.net.Socket;

public class ConcurrentReportCommand extends ConcurrentCommand {

    public ConcurrentReportCommand(Socket socket, String[] command) {
        super(socket, command);
    }

    @Override
    public String execute() {
        WDIDAO dao = WDIDAO.getDAO();
        return dao.report(command[3]);
    }

}
