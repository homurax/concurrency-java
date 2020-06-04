package com.homurax.advancedServer.parallel.cache;

import lombok.Data;

import java.util.Date;

@Data
public class CacheItem {

    private final String command;

    private final String response;

    private final Date creationDate;

    private Date accessDate;

    public CacheItem(String command, String response) {
        creationDate = new Date();
        accessDate = new Date();
        this.command = command;
        this.response = response;
    }
}
