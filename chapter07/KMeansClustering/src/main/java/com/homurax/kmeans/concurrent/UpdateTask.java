package com.homurax.kmeans.concurrent;

import com.homurax.kmeans.serial.DocumentCluster;

import java.util.concurrent.RecursiveAction;

public class UpdateTask extends RecursiveAction {

    private static final long serialVersionUID = 1005287726225369344L;

    private final DocumentCluster[] clusters;
    private final int start;
    private final int end;
    private final int maxSize;

    public UpdateTask(DocumentCluster[] clusters, int start, int end, int maxSize) {
        this.clusters = clusters;
        this.start = start;
        this.end = end;
        this.maxSize = maxSize;
    }

    @Override
    protected void compute() {
        if (end - start <= maxSize) {
            for (int i = start; i < end; i++) {
                clusters[i].calculateCentroid();
            }
        } else {
            int mid = (start + end) / 2;
            UpdateTask task1 = new UpdateTask(clusters, start, mid, maxSize);
            UpdateTask task2 = new UpdateTask(clusters, mid, end, maxSize);
            invokeAll(task1, task2);
        }
    }

}
