package com.homurax.chapter07.filter.concurrent;

import com.homurax.chapter07.filter.common.CensusData;
import com.homurax.chapter07.filter.common.Filter;
import com.homurax.chapter07.filter.common.FilterData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.RecursiveTask;

public class ListTask extends RecursiveTask<List<CensusData>> {

    private static final long serialVersionUID = -4106884399809219741L;

    private CensusData[] data;
    private int start, end, size;
    private List<FilterData> filters;
    private TaskManager manager;

    public ListTask(CensusData[] data, int start, int end, TaskManager manager, int size, List<FilterData> filters) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.size = size;
        this.filters = filters;
        this.manager = manager;
    }

    @Override
    protected List<CensusData> compute() {
        List<CensusData> result = new ArrayList<>();
        List<CensusData> tmp;
        if (end - start <= size) {
            for (int i = start; i < end; i++) {
                CensusData censusData = data[i];
                if (Filter.filter(censusData, filters)) {
                    result.add(censusData);
                }
            }
        } else {
            int mid = (start + end) / 2;
            ListTask task1 = new ListTask(data, start, mid, manager, size, filters);
            ListTask task2 = new ListTask(data, mid, end, manager, size, filters);
            manager.addTask(task1);
            manager.addTask(task2);
            manager.deleteTask(this);
            task1.fork();
            task2.fork();
            task2.quietlyJoin();
            task1.quietlyJoin();
            manager.deleteTask(task1);
            manager.deleteTask(task2);

            try {
                tmp = task1.join();
                if (tmp != null)
                    result.addAll(tmp);
                manager.deleteTask(task1);
            } catch (CancellationException ex) {
            }
            try {
                tmp = task2.join();
                if (tmp != null)
                    result.addAll(tmp);
                manager.deleteTask(task2);
            } catch (CancellationException ex) {
            }
        }

        return result;
    }

}
