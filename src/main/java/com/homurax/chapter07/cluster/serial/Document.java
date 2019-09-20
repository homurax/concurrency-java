package com.homurax.chapter07.cluster.serial;

import com.homurax.chapter07.cluster.common.Word;
import lombok.Data;

@Data
public class Document {

    private Word[] data;
    private String name;
    private DocumentCluster cluster;

    public Document(String name, int size) {
        this.name = name;
        this.data = new Word[size];
        this.cluster = null;
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
