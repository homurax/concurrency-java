package com.homurax.objectFilter.concurrent;

import com.homurax.objectFilter.common.data.CensusData;
import com.homurax.objectFilter.common.data.Filter;
import com.homurax.objectFilter.common.data.FilterData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.RecursiveTask;

public class ListTask extends RecursiveTask<List<CensusData>> {

    private static final long serialVersionUID = -4106884399809219741L;

    private final CensusData[] data;
    private final int start;
    private final int end;
    private final int size;
    private final List<FilterData> filters;
    private final TaskManager manager;

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
        List<CensusData> ret = new ArrayList<>();
        List<CensusData> tmp;
        if (end - start <= size) {
            for (int i = start; i < end; i++) {
                CensusData censusData = data[i];
                if (Filter.filter(censusData, filters)) {
                    ret.add(censusData);
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
                if (tmp != null) {
                    ret.addAll(tmp);
                }
                manager.deleteTask(task1);
            } catch (CancellationException ignored) {
            }
            try {
                tmp = task2.join();
                if (tmp != null) {
                    ret.addAll(tmp);
                }
                manager.deleteTask(task2);
            } catch (CancellationException ignored) {
            }
        }

        return ret;
    }

}
