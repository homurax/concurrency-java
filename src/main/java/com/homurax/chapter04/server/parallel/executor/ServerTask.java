package com.homurax.chapter04.server.parallel.executor;

import com.homurax.chapter04.server.parallel.command.ConcurrentCommand;
import lombok.Data;

import java.util.concurrent.FutureTask;

@Data
public class ServerTask<V> extends FutureTask<V> implements Comparable<ServerTask<V>> {

    private ConcurrentCommand command;

    public ServerTask(ConcurrentCommand command) {
        super(command, null);
        this.command = command;
    }

    @Override
    public int compareTo(ServerTask<V> other) {
        return command.compareTo(other.getCommand());
    }
}
