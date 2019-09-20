package com.homurax.chapter06.keyword.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Document {

    private String fileName;

    private Map<String, Word> voc;

    public Document() {
        voc = new HashMap<>();
    }

    public void addWord(String string) {
        voc.computeIfAbsent(string, Word::new).addTf();
    }

    @Override
    public String toString() {
        return this.fileName + ": " + voc.size();
    }

}
