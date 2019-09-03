package com.homurax.chapter02.search.parallel;

import com.homurax.chapter02.search.util.Result;

import java.io.File;
import java.lang.Thread.State;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelGroupFileSearch {

    public static void searchFiles(File file, String fileName, Result parallelResult) {

        ConcurrentLinkedQueue<File> directories = new ConcurrentLinkedQueue<>();
        File[] contents = file.listFiles();
        for (File content : contents) {
            if (content.isDirectory()) {
                directories.add(content);
            }
        }

        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];
        ParallelGroupFileTask[] tasks = new ParallelGroupFileTask[numThreads];
        for (int i = 0; i < numThreads; i++) {
            tasks[i] = new ParallelGroupFileTask(directories, fileName, parallelResult);
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        boolean finish = false;
        int terminatedNum = 0;
        while (!finish) {
            for (int i = 0; i < numThreads; i++) {
                if (threads[i].getState() == State.TERMINATED) {
                    terminatedNum++;
                    if (tasks[i].getFound()) {
                        finish = true;
                    }
                }
            }
            if (terminatedNum == numThreads) {
                finish = true;
            }
        }

        // Interrupt the remaining threads.
        if (terminatedNum != numThreads) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }
    }
}
