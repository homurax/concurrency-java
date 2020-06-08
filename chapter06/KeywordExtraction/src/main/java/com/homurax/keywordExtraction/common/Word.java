package com.homurax.keywordExtraction.common;

import lombok.Data;

@Data
public class Word implements Comparable<Word> {

    private String word;
    private int tf;
    private int df;
    private double tfIdf;

    public Word(String word) {
        this.word = word;
        this.df = 1;
    }

    public void addTf() {
        this.tf++;
    }

    public void setDf(int df, int N) {
        this.df = df;
        this.tfIdf = this.tf * Math.log((double) N / df);
    }

    @Override
    public int compareTo(Word o) {
        return Double.compare(o.getTfIdf(), this.getTfIdf());
    }

    public Word merge(Word other) {
        if (this.word.equals(other.word)) {
            this.tf += other.tf;
            this.df += other.df;
        }
        return this;
    }

}
