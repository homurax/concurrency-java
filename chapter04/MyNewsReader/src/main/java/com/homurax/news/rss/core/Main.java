package com.homurax.news.rss.core;

import com.homurax.news.rss.reader.basic.NewsSystem;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        NewsSystem system = new NewsSystem("data\\sources.txt");
        Thread thread = new Thread(system);
        thread.start();

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.shutdown();
    }

}
