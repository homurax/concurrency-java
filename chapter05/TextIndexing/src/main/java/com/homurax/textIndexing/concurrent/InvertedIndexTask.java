package com.homurax.textIndexing.concurrent;

import com.homurax.textIndexing.common.Document;

import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class InvertedIndexTask implements Runnable {

    private final CompletionService<Document> completionService;
    private final ConcurrentHashMap<String, StringBuffer> invertedIndex;

    public InvertedIndexTask(CompletionService<Document> completionService,
                             ConcurrentHashMap<String, StringBuffer> invertedIndex) {
        this.completionService = completionService;
        this.invertedIndex = invertedIndex;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                try {
                    Document document = completionService.take().get();
                    updateInvertedIndex(document.getVoc(), invertedIndex, document.getFileName());
                } catch (InterruptedException e) {
                    break;
                }
            }
            while (true) {
                Future<Document> future = completionService.poll();
                if (future == null) {
                    break;
                }
                Document document = future.get();
                updateInvertedIndex(document.getVoc(), invertedIndex, document.getFileName());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void updateInvertedIndex(Map<String, Integer> voc,
                                     ConcurrentHashMap<String, StringBuffer> invertedIndex,
                                     String fileName) {

        for (String word : voc.keySet()) {
            if (word.length() >= 3) {
                StringBuffer buffer = invertedIndex.computeIfAbsent(word, k -> new StringBuffer());
                buffer.append(fileName).append(";");
            }
        }
    }

}
