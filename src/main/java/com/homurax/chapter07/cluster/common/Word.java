package com.homurax.chapter07.cluster.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Word implements Comparable<Word> {

    private int index;
    private double tfidf;

    @Override
    public int compareTo(Word o) {
        return Integer.compare(this.getIndex(),  o.getIndex());
    }

}
