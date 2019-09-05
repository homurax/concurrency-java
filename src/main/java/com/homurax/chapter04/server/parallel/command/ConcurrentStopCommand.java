package com.homurax.chapter04.server.parallel.command;

import com.homurax.chapter04.server.parallel.server.ConcurrentServer;

import java.net.Socket;


public class ConcurrentStopCommand extends ConcurrentCommand {

	public ConcurrentStopCommand (Socket socket, String [] command) {
		super (socket, command);
		setCacheable(false);
	}

	@Override
	public String execute() {
		ConcurrentServer.shutdown();
		return "Server stopped";
	}

}
