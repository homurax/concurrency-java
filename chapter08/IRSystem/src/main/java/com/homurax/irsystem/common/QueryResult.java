package com.homurax.irsystem.common;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class QueryResult {

    private final Map<String, Document> results;

    public QueryResult(Map<String, Document> results) {
        this.results = results;
    }

    public void append(Token token) {
        results.computeIfAbsent(token.getFile(), Document::new).addTfxidf(token.getTfxidf());
    }

    public List<Document> getAsList() {
        return new ArrayList<>(results.values());
    }

}
