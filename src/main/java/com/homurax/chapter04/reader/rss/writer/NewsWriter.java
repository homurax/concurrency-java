package com.homurax.chapter04.reader.rss.writer;

import com.homurax.chapter04.reader.rss.buffer.NewsBuffer;
import com.homurax.chapter04.reader.rss.data.CommonInformationItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NewsWriter implements Runnable {

    private NewsBuffer buffer;

    public NewsWriter(NewsBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {

                CommonInformationItem item = buffer.get();
                Path path = Paths.get(System.getProperty("user.dir"), item.getFileName());
                try (BufferedWriter fileWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
                    fileWriter.write(item.toXML());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            //Normal execution
        }
    }

}
