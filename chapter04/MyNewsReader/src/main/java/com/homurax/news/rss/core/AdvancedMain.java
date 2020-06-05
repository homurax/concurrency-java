package com.homurax.news.rss.core;

import com.homurax.news.rss.reader.advanced.NewsAdvancedSystem;

import java.util.concurrent.TimeUnit;

public class AdvancedMain {

    public static void main(String[] args) {

        NewsAdvancedSystem system = new NewsAdvancedSystem("data\\sources.txt");
        Thread thread = new Thread(system);
        thread.start();

        try {
            TimeUnit.MINUTES.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.shutdown();
    }

}
