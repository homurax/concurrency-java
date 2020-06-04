package com.homurax.advancedServer.common;

public abstract class Command {

    protected String[] command;

    private boolean cacheable;

    public Command(String[] command) {
        this.command = command;
        cacheable = true;
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    public abstract String execute();
}
