package com.homurax.chapter07.filter.concurrent;

import com.homurax.chapter07.filter.common.CensusData;
import com.homurax.chapter07.filter.common.Filter;
import com.homurax.chapter07.filter.common.FilterData;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.RecursiveTask;

public class IndividualTask extends RecursiveTask<CensusData> {

	private static final long serialVersionUID = -4106884399809219741L;

	private CensusData[] data;
	private int start, end, size;
	private TaskManager manager;
	private List<FilterData> filters;

	public IndividualTask(CensusData[] data, int start, int end, TaskManager manager, int size,
			List<FilterData> filters) {
		this.data = data;
		this.start = start;
		this.end = end;
		this.manager = manager;
		this.size = size;
		this.filters = filters;
	}

	@Override
	protected CensusData compute() {
		if (end - start <= size) {
			for (int i = start; i < end && !Thread.currentThread().isInterrupted(); i++) {
				CensusData censusData = data[i];
				if (Filter.filter(censusData, filters)) {
					System.out.println("Found: " + i);
					manager.cancelTasks(this);
					return censusData;
				}
			}
			return null;
		} else {
			int mid = (start + end) / 2;
			IndividualTask task1 = new IndividualTask(data, start, mid, manager, size, filters);
			IndividualTask task2 = new IndividualTask(data, mid, end, manager, size, filters);
			manager.addTask(task1);
			manager.addTask(task2);
			manager.deleteTask(this);
			task1.fork();
			task2.fork();
			task1.quietlyJoin();
			task2.quietlyJoin();
			manager.deleteTask(task1);
			manager.deleteTask(task2);

			try {
				CensusData result = task1.join();
				if (result != null)
					return result;
				manager.deleteTask(task1);
			} catch (CancellationException ex) {
			}
			try {
				CensusData result = task2.join();
				if (result != null)
					return result;
				manager.deleteTask(task2);
			} catch (CancellationException ex) {
			}
			return null;
		}
	}

}
