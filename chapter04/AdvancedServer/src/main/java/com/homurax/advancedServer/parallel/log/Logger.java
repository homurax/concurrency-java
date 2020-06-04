package com.homurax.advancedServer.parallel.log;

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

    private static final ConcurrentLinkedQueue<String> logQueue = new ConcurrentLinkedQueue<>();

    private static final Thread thread;

    private static final String ROUTE = "output\\server.log";

    static {
        LogTask task = new LogTask();
        thread = new Thread(task);
        thread.start();
    }


    public static void sendMessage(String message) {

        StringWriter writer = new StringWriter();
        writer.write(new Date().toString());
        writer.write(": ");
        writer.write(message);
        logQueue.offer(writer.toString());
    }


    public static void writeLogs() {
        String message;
        Path path = Paths.get(ROUTE);
        try (BufferedWriter fileWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            while ((message = logQueue.poll()) != null) {
                StringWriter writer = new StringWriter();
                writer.write(new Date().toString());
                writer.write(": ");
                writer.write(message);
                fileWriter.write(writer.toString());
                fileWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initializeLog() {
        Path path = Paths.get(ROUTE);
        if (Files.exists(path)) {
            try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.TRUNCATE_EXISTING)) {

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void shutdown() {
        writeLogs();
        thread.interrupt();
    }
}
