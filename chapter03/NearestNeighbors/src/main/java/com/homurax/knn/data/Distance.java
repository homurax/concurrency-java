package com.homurax.knn.data;

import lombok.Data;

@Data
public class Distance implements Comparable<Distance> {

    private int index;
    private double distance;

    @Override
    public int compareTo(Distance other) {
        return Double.compare(this.distance, other.getDistance());
    }
}
