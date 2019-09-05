package com.homurax.chapter03.server.parallel.log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Logger {

    private static ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue<>();

    private static Thread thread;

    private static final String LOG_FILE = Paths.get("src\\main\\java\\com\\homurax\\chapter03\\server\\parallel\\log\\server.log").toString();

    static {
        LogTask task = new LogTask();
        thread = new Thread(task);
    }


    public static void sendMessage(String message) {
        logQueue.offer(new Date() + ": " + message);
    }

    public static void writeLogs() {
        String message;
        Path path = Paths.get(LOG_FILE);
        try (BufferedWriter fileWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            while ((message = logQueue.poll()) != null) {
                fileWriter.write(new Date() + ": " + message);
                fileWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initializeLog() {
        Path path = Paths.get(LOG_FILE);
        if (Files.exists(path)) {
            try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING)) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        thread.start();
    }

    public static void shutdown() {
        thread.interrupt();
    }
}
