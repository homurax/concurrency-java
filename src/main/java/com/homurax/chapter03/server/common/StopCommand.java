package com.homurax.chapter03.server.common;

public class StopCommand extends Command {

	public StopCommand (String [] command) {
		super (command);
	}
	
	@Override
	public String execute() {
		return "Server stopped";
	}
}
