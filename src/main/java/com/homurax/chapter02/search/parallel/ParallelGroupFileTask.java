package com.homurax.chapter02.search.parallel;

import com.homurax.chapter02.search.util.Result;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParallelGroupFileTask implements Runnable {

    private final String fileName;
    private final ConcurrentLinkedQueue<File> directories;
    private final Result parallelResult;
    private boolean found = false;

    public ParallelGroupFileTask(ConcurrentLinkedQueue<File> directories, String fileName, Result parallelResult) {
        this.directories = directories;
        this.fileName = fileName;
        this.parallelResult = parallelResult;
    }

    @Override
    public void run() {
        while (directories.size() > 0) {
            try {
                processDirectory(directories.poll(), fileName, parallelResult);
                if (this.found) {
                    System.out.printf("%s has found the file%n", Thread.currentThread().getName());
                    System.out.printf("Parallel Search: Path: %s%n", parallelResult.getPath());
                    return;
                }
            } catch (InterruptedException e) {
                System.out.printf("%s has been interrupted%n", Thread.currentThread().getName());
            }
        }
    }

    private void processDirectory(File file, String fileName, Result parallelResult) throws InterruptedException {

        File[] contents = file.listFiles();
        if (contents == null || contents.length == 0) {
            return;
        }

        for (File content : contents) {
            if (content.isDirectory()) {
                processDirectory(content, fileName, parallelResult);
            } else {
                if (fileName.equals(content.getName())) {
                    this.found = true;
                    parallelResult.setPath(content.getAbsolutePath());
                }
            }
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
            if (this.found) {
                return;
            }
        }
    }

    public boolean getFound() {
        return found;
    }

}
