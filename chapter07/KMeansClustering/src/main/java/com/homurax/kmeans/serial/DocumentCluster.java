package com.homurax.kmeans.serial;

import com.homurax.kmeans.common.data.Word;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

@Getter
public class DocumentCluster {

    private final double[] centroid;

    private final Collection<Document> documents;

    public DocumentCluster(int size, Collection<Document> documents) {
        this.documents = documents;
        this.centroid = new double[size];
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void clearClusters() {
        documents.clear();
    }

    public void calculateCentroid() {

        Arrays.fill(centroid, 0);
        for (Document document : documents) {
            Word[] vector = document.getData();
            for (Word word : vector) {
                centroid[word.getIndex()] += word.getTfidf();
            }
        }

        for (int i = 0; i < centroid.length; i++) {
            centroid[i] /= documents.size();
        }
    }

    public void initialize(Random random) {
        for (int i = 0; i < centroid.length; i++) {
            centroid[i] = random.nextDouble();
        }
    }

    public int getDocumentCount() {
        return documents.size();
    }
}
