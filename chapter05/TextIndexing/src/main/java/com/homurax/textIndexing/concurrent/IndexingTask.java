package com.homurax.textIndexing.concurrent;

import com.homurax.textIndexing.common.Document;
import com.homurax.textIndexing.common.DocumentParser;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;

public class IndexingTask implements Callable<Document> {

    private final File file;

    public IndexingTask(File file) {
        this.file = file;
    }

    @Override
    public Document call() throws Exception {
        DocumentParser parser = new DocumentParser();
        Map<String, Integer> voc = parser.parse(file.getAbsolutePath());
        Document document = new Document();
        document.setFileName(file.getName());
        document.setVoc(voc);
        return document;
    }

}
