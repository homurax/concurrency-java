package com.homurax.mergeSort.concurrent;

import com.homurax.mergeSort.serial.SerialMergeSort;

import java.util.concurrent.CountedCompleter;

public class MergeSortTask extends CountedCompleter<Void> {

    private static final long serialVersionUID = -5183127767439978300L;
    private final Comparable[] data;
    private final int start;
    private final int end;
    private int middle;

    public MergeSortTask(Comparable[] data, int start, int end, MergeSortTask parent) {
        super(parent);
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public void compute() {
        if (end - start >= 1024) {
            middle = (start + end) >>> 1;
            MergeSortTask task1 = new MergeSortTask(data, start, middle, this);
            MergeSortTask task2 = new MergeSortTask(data, middle, end, this);
            addToPendingCount(1);
            task1.fork();
            task2.fork();
        } else {
            SerialMergeSort.mergeSort(data, start, end);
            tryComplete();
        }
    }

    @Override
    public void onCompletion(CountedCompleter<?> caller) {

        if (middle == 0) {
            return;
        }

        int length = end - start + 1;
        int i = start;
        int j = middle;
        int index = 0;
        Comparable[] tmp = new Comparable[length];

        while ((i < middle) && (j < end)) {
            if (data[i].compareTo(data[j]) <= 0) {
                tmp[index++] = data[i++];
            } else {
                tmp[index++] = data[j++];
            }
        }

        while (i < middle) {
            tmp[index++] = data[i++];
        }

        while (j < end) {
            tmp[index++] = data[j++];
        }

        for (index = 0; index < (end - start); index++) {
            data[index + start] = tmp[index];
        }
    }

}
