package com.homurax.chapter03.nearest.knn.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Distance implements Comparable<Distance> {

    private int index;

    private double distance;

    @Override
    public int compareTo(Distance other) {
        if (this.distance < other.getDistance()) {
            return -1;
        } else if (this.distance > other.getDistance()) {
            return 1;
        }
        return 0;
    }
}
