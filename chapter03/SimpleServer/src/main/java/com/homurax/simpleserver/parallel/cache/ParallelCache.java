package com.homurax.simpleserver.parallel.cache;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ParallelCache {

    private final ConcurrentHashMap<String, CacheItem> cache;

    private final Thread thread;

    public static int MAX_LIVING_TIME_MILLIS = 600_000;

    public ParallelCache() {
        cache = new ConcurrentHashMap<>();
        CleanCacheTask task = new CleanCacheTask(this);
        thread = new Thread(task);
        thread.start();
    }


    public void put(String command, String response) {
        CacheItem item = new CacheItem(command, response);
        cache.put(command, item);
    }

    public String get(String command) {
        CacheItem item = cache.get(command);
        if (item == null) {
            return null;
        }
        item.setAccessDate(new Date());
        return item.getResponse();
    }

    public void cleanCache() {
        Date revisionDate = new Date();
        cache.values().removeIf(item -> revisionDate.getTime() - item.getAccessDate().getTime() > MAX_LIVING_TIME_MILLIS);
    }

    public void shutdown() {
        thread.interrupt();
    }

    public int getItemCount() {
        return cache.size();
    }
}
