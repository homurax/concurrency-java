package com.homurax.irsystem.common;

import lombok.Getter;

import java.util.concurrent.atomic.DoubleAdder;

@Getter
public class Document implements Comparable<Document> {

    private final String documentName;

    private final DoubleAdder tfxidf;

    public Document(String documentName) {
        this.documentName = documentName;
        this.tfxidf = new DoubleAdder();
    }

    public void addTfxidf(double value) {
        tfxidf.add(value);
    }

    @Override
    public int compareTo(Document o) {
        return Double.compare(o.getTfxidf().doubleValue(), tfxidf.doubleValue());
    }

    @Override
    public String toString() {
        return documentName + ": " + tfxidf.doubleValue();
    }

}
