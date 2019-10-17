package com.homurax.chapter11.structure.hash.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasicProduct implements Comparable<BasicProduct> {

    private String id;
    private String asin;
    private String title;

    @Override
    public int compareTo(BasicProduct other) {
        System.out.println(other.getAsin().compareTo(getAsin()));
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
