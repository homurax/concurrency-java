package com.homurax.chapter04.reader.rss.reader.advanced;

import com.homurax.chapter04.reader.rss.buffer.NewsBuffer;
import com.homurax.chapter04.reader.rss.reader.basic.NewsTask;
import com.homurax.chapter04.reader.rss.writer.NewsWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NewsAdvancedSystem implements Runnable {

    private String route;

    private NewsExecutor executor;

    private NewsBuffer buffer;

    private CountDownLatch latch = new CountDownLatch(1);

    public NewsAdvancedSystem(String route) {
        this.route = route;
        this.executor = new NewsExecutor(Runtime.getRuntime().availableProcessors());
        this.buffer = new NewsBuffer();
    }

    @Override
    public void run() {

        Path file = Paths.get(route);
        NewsWriter newsWriter = new NewsWriter(buffer);
        Thread thread = new Thread(newsWriter);
        thread.start();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                String name = data[0];
                String url = data[1];
                NewsTask task = new NewsTask(name, url, buffer);
                System.out.println("Task " + task.getName());
                executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Shutting down the executor.");
        executor.shutdown();
        thread.interrupt();
        System.out.println("The system has finished.");
    }

    public void shutdown() {
        latch.countDown();
    }
}
