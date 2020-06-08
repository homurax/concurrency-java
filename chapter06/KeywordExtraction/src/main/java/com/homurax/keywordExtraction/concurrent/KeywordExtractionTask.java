package com.homurax.keywordExtraction.concurrent;

import com.homurax.keywordExtraction.common.Document;
import com.homurax.keywordExtraction.common.DocumentParser;
import com.homurax.keywordExtraction.common.Keyword;
import com.homurax.keywordExtraction.common.Word;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Phaser;

public class KeywordExtractionTask implements Runnable {

    private final ConcurrentHashMap<String, Word> globalVoc;
    private final ConcurrentHashMap<String, Integer> globalKeywords;
    private final ConcurrentLinkedDeque<File> concurrentFileListPhase1;
    private final ConcurrentLinkedDeque<File> concurrentFileListPhase2;
    private final Phaser phaser;
    private final String name;
    private final boolean main;
    private int parsedDocuments;
    private final int numDocuments;


    public KeywordExtractionTask(
            ConcurrentLinkedDeque<File> concurrentFileListPhase1,
            ConcurrentLinkedDeque<File> concurrentFileListPhase2,
            Phaser phaser, ConcurrentHashMap<String, Word> globalVoc,
            ConcurrentHashMap<String, Integer> globalKeywords,
            int numDocuments, String name, boolean main) {

        this.concurrentFileListPhase1 = concurrentFileListPhase1;
        this.concurrentFileListPhase2 = concurrentFileListPhase2;
        this.globalVoc = globalVoc;
        this.globalKeywords = globalKeywords;
        this.phaser = phaser;
        this.main = main;
        this.name = name;
        this.numDocuments = numDocuments;
        System.out.println(name + ": " + main);
    }

    @Override
    public void run() {

        File file;

        // Phase 1
        phaser.arriveAndAwaitAdvance();
        System.out.println(name + ": Phase 1");
        while ((file = concurrentFileListPhase1.poll()) != null) {
            Document doc = DocumentParser.parse(file.getAbsolutePath());
            for (Word word : doc.getVoc().values()) {
                globalVoc.merge(word.getWord(), word, Word::merge);
            }
            parsedDocuments++;
        }

        System.out.println(name + ": " + parsedDocuments + " parsed.");


        // Phase 2
        phaser.arriveAndAwaitAdvance();
        System.out.println(name + ": Phase 2");

        while ((file = concurrentFileListPhase2.poll()) != null) {
            Document doc = DocumentParser.parse(file.getAbsolutePath());
            List<Word> keywords = new ArrayList<>(doc.getVoc().values());

            for (Word word : keywords) {
                Word globalWord = globalVoc.get(word.getWord());
                word.setDf(globalWord.getDf(), numDocuments);
            }

            Collections.sort(keywords);

            if (keywords.size() > 10) keywords = keywords.subList(0, 10);
            for (Word word : keywords) {
                addKeyword(globalKeywords, word.getWord());
            }
        }
        System.out.println(name + ": " + parsedDocuments + " parsed.");


        if (main) {
            // Phase 3
            phaser.arriveAndAwaitAdvance();
            Iterator<Entry<String, Integer>> iterator = globalKeywords.entrySet().iterator();
            Keyword[] orderedGlobalKeywords = new Keyword[globalKeywords.size()];
            int index = 0;
            while (iterator.hasNext()) {
                Entry<String, Integer> entry = iterator.next();
                Keyword keyword = new Keyword();
                keyword.setWord(entry.getKey());
                keyword.setDf(entry.getValue());
                orderedGlobalKeywords[index] = keyword;
                index++;
            }

            System.out.println("Keyword Size: " + orderedGlobalKeywords.length);

            Arrays.parallelSort(orderedGlobalKeywords);

            int counter = 0;
            for (Keyword keyword : orderedGlobalKeywords) {
                System.out.println(keyword.getWord() + ": " + keyword.getDf());
                counter++;
                if (counter == 100) {
                    break;
                }
            }
        }
        phaser.arriveAndDeregister();

        System.out.println("Thread " + name + " has finished.");
    }

    private synchronized void addKeyword(
            Map<String, Integer> globalKeywords, String word) {
        globalKeywords.merge(word, 1, Integer::sum);
    }
}
