package com.homurax.chapter04.reader.rss.reader.basic;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        NewsSystem system = new NewsSystem("src\\main\\java\\com\\homurax\\chapter04\\reader\\data\\sources.txt");

        Thread thread = new Thread(system);
        thread.start();

        try {
            TimeUnit.MINUTES.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.shutdown();
    }

}
