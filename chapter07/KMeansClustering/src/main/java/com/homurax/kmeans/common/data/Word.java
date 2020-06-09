package com.homurax.kmeans.common.data;

import lombok.Data;

@Data
public class Word implements Comparable<Word> {

    private int index;
    private double tfidf;

    @Override
    public int compareTo(Word o) {
        return Integer.compare(this.getIndex(), o.getIndex());
    }
}
