package com.homurax.mergeSort.common;

import lombok.Data;

@Data
public class AmazonMetaData implements Comparable<AmazonMetaData> {

    private int id;
    private String ASIN;
    private String title;
    private String group;
    private long salesrank;
    private int reviews;
    private int similar;
    private int categories;

    @Override
    public int compareTo(AmazonMetaData other) {
        return Long.compare(this.getSalesrank(), other.getSalesrank());
    }

}
