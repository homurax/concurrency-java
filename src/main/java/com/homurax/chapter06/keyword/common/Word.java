package com.homurax.chapter06.keyword.common;

import lombok.Data;

@Data
public class Word implements Comparable<Word> {

    private String word;
    private int tf; // 术语频次
    private int df; // 文档频次
    private double tfIdf;

    public Word(String word) {
        this.word = word;
        this.df = 1;
    }

    public void addTf() {
        this.tf++;
    }

    /**
     * TF-IDF = TF x IDF = Ftd x log(N/nt)
     */
    public void setDf(int df, int N) {
        this.df = df;
        this.tfIdf = tf * Math.log(Double.valueOf(N) / df);
    }

    public Word merge(Word other) {
        if (this.word.equals(other.word)) {
            this.tf += other.tf;
            this.df += other.df;
        }
        return this;
    }

    @Override
    public int compareTo(Word o) {
        return Double.compare(o.getTfIdf(), this.getTfIdf());
    }
}
