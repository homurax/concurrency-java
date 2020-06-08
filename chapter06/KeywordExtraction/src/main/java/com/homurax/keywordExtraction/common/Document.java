package com.homurax.keywordExtraction.common;

import lombok.Data;

import java.util.HashMap;

@Data
public class Document {

    private String fileName;

    private HashMap<String, Word> voc;

    public Document() {
		this.voc = new HashMap<>();
    }

    public void addWord(String string) {
        this.voc.computeIfAbsent(string, Word::new).addTf();
    }

    @Override
    public String toString() {
        return fileName + ": " + voc.size();
    }
}
