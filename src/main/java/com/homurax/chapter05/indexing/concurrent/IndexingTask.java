package com.homurax.chapter05.indexing.concurrent;

import com.homurax.chapter05.indexing.common.Document;
import com.homurax.chapter05.indexing.common.DocumentParser;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Callable;

public class IndexingTask implements Callable<Document> {

    private File file;

    public IndexingTask(File file) {
        this.file = file;
    }

    @Override
    public Document call() throws Exception {

        DocumentParser parser = new DocumentParser();
        Map<String, Integer> voc = parser.parse(file.getAbsolutePath());
        return new Document(file.getName(), voc);
    }

}
