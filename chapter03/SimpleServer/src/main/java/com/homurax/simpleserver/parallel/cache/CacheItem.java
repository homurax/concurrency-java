package com.homurax.simpleserver.parallel.cache;

import lombok.Data;

import java.util.Date;

@Data
public class CacheItem {

    private String command;

    private String response;

    private Date creationDate;

    private Date accessDate;

    public CacheItem(String command, String response) {
        creationDate = new Date();
        accessDate = new Date();
        this.command = command;
        this.response = response;
    }
}
