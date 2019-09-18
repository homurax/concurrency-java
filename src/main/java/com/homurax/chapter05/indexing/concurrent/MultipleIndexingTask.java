package com.homurax.chapter05.indexing.concurrent;

import com.homurax.chapter05.indexing.common.Document;
import com.homurax.chapter05.indexing.common.DocumentParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class MultipleIndexingTask implements Callable<List<Document>> {

    private List<File> files;

    public MultipleIndexingTask(List<File> files) {
        this.files = files;
    }

    @Override
    public List<Document> call() throws Exception {

        List<Document> documents = new ArrayList<>();
        DocumentParser parser = new DocumentParser();
        for (File file : files) {
            Map<String, Integer> voc = parser.parse(file.getAbsolutePath());
            Document document = new Document(file.getName(), voc);
            documents.add(document);
        }
        return documents;
    }

}
