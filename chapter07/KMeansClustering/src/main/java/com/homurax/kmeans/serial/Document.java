package com.homurax.kmeans.serial;

import com.homurax.kmeans.common.data.Word;
import lombok.Data;

@Data
public class Document {

    private Word[] data;
    private String name;
    private DocumentCluster cluster;

    public Document(String name, int size) {
        this.name = name;
        this.data = new Word[size];
    }

    public boolean setCluster(DocumentCluster cluster) {
        if (this.cluster == cluster) {
            return false;
        } else {
            this.cluster = cluster;
            return true;
        }
    }
}
