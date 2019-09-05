package com.homurax.chapter03.server.parallel.cache;

import lombok.Data;

import java.util.Date;

@Data
public class CacheItem {

    private String command;

    private String response;

    private Date creationDate;

    private Date accessDate;

    public CacheItem(String command, String response) {
        this.creationDate = new Date();
        this.accessDate = new Date();
        this.command = command;
        this.response = response;
    }
}
