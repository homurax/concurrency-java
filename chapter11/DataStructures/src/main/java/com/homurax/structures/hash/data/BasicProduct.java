package com.homurax.structures.hash.data;

import lombok.Data;

@Data
public class BasicProduct implements Comparable<BasicProduct> {

    private String id;
    private String asin;
    private String title;

    @Override
    public int compareTo(BasicProduct other) {
        return other.getAsin().compareTo(getAsin());
    }

    @Override
    public boolean equals(Object object) {
        BasicProduct other = (BasicProduct) object;
        return asin.equals(other.getAsin());
    }

    @Override
    public int hashCode() {
        return asin.hashCode();
    }
}
