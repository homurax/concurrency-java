package com.homurax.keywordExtraction.common;

import lombok.Data;

@Data
public class Keyword implements Comparable<Keyword> {

    private String word;
    private int df;

    @Override
    public int compareTo(Keyword o) {
        return Integer.compare(o.getDf(), this.getDf());
    }

}
