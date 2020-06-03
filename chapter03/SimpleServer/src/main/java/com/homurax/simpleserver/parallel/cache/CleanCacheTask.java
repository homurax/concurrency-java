package com.homurax.simpleserver.parallel.cache;

import java.util.concurrent.TimeUnit;


public class CleanCacheTask implements Runnable {

    private final ParallelCache cache;

    public CleanCacheTask(ParallelCache cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.SECONDS.sleep(10);
                cache.cleanCache();
            }
        } catch (InterruptedException e) {

        }
    }

}
