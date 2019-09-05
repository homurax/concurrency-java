package com.homurax.chapter03.server.common;

/**
 * Abstract class that includes the necessary elements of every Command
 */
public abstract class Command {

    protected final String[] command;

    private boolean cacheable;

    public Command(String[] command) {
        this.command = command;
        setCacheable(true);
    }


    public abstract String execute();

    public boolean isCacheable() {
        return this.cacheable;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

}
