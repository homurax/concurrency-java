package com.homurax.news.rss.reader.advanced;

import com.homurax.news.rss.buffer.NewsBuffer;
import com.homurax.news.rss.reader.basic.NewsTask;
import com.homurax.news.rss.writer.NewsWriter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NewsAdvancedSystem implements Runnable {

    private final String route;

    private final NewsExecutor executor;

    private final NewsBuffer buffer;

    private final CountDownLatch latch = new CountDownLatch(1);

    public NewsAdvancedSystem(String route) {
        this.route = route;
        executor = new NewsExecutor(Runtime.getRuntime().availableProcessors());
        buffer = new NewsBuffer();
    }


    @Override
    public void run() {
        Path file = Paths.get(route);
        NewsWriter newsWriter = new NewsWriter(buffer);
        Thread t = new Thread(newsWriter);
        t.start();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] data = line.split(";");
                NewsTask task = new NewsTask(data[0], data[1], buffer);
                System.out.println("Task " + task.getName());
                executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
            }
        } catch (Exception x) {
            x.printStackTrace();
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
        t.interrupt();
        System.out.println("The system has finished.");
    }

    public void shutdown() {
        latch.countDown();
    }
}
