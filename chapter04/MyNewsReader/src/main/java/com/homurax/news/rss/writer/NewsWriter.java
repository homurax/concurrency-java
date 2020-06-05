package com.homurax.news.rss.writer;

import com.homurax.news.rss.buffer.NewsBuffer;
import com.homurax.news.rss.data.CommonInformationItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NewsWriter implements Runnable {

    private final NewsBuffer buffer;

    public NewsWriter(NewsBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                CommonInformationItem item = buffer.get();
                Path path = Paths.get("output\\" + item.getFileName());
                try (BufferedWriter fileWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
                    fileWriter.write(item.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            // Normal execution
        }
    }

}
