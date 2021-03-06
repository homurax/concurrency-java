# 优化分治解决方案：Fork/Join 框架

Java 7 并发 API 通过 Fork/Join 框架引入了一种特殊的执行器。该框架的设计目的是针对那些可以使用分治设计范式来解决的问题，实现最优的并发解决方案。

## Fork/Join 框架简介

该框架基于 ForkJoinPool 类，该类是一种特殊的执行器，具有 `fork()` 方法和 `join()` 方法两个操作（以及它们的不同变体），以及一个被称作工作窃取算法的内部算法。



### Fork/Join 框架的基本特征

Fork/Join 框架用于解决基于分治方法的问题。必须将原始问题划分为较小的问题， 直到问题很小，可以直接解决。有了这个框架，待实现任务的主方法便如下所示：

```java
if (problem.size() > DEFAULT_SIZE) {
    divideTasks();
    executeTask();
    taskResults = joinTasksResult();
    return taskResults;
} else {
    taskResults = solveBasicProblem();
    return taskResults;
}
```

该方法最大的好处是可以高效分割和执行子任务，并且获取子任务的结果以计算父任务的结果。 该功能由 ForkJoinTask 类提供的如下两个方法支持。

- **`fork()`**：该方法可以将一个子任务发送给 Fork/Join 执行器。
- **`join()`** ：该方法可以等待一个子任务执行结束后返回其结果。

Fork/Join 框架还有另一个关键特性，即工作窃取算法。该算法确定要执行的任务。当一个任务使用 `join()` 方法等待某个子任务结束时，执行该任务的线程将会从任务池中选取另一个等待执行的任务并且开始执行。通过这种方式，Fork/Join 执行器的线程总是通过改进应用程序的性能来执行任务。

可以通过调用静态方法 `ForkJoinPool.commonPool()` 获得公用池，而不需要采用显式方法创建。这种默认的 Fork/Join 执行器会自动使用由计算机的可用处理器确定的线程数。可以通过更改系统属性值 `java.util.concurrent.ForkJoinPool.common.parallelism` 来修改这一默认行为。



### Fork/Join 框架的局限性

- 不再进行细分的基本问题的规模既不能过大也不能过小。按照 Java API 文档的说明，该基本问题的规模应该介于 **100 到 10000** 个基本计算步骤之间。
- 数据可用前，不应使用阻塞型 I/O 操作，例如读取用户输入或者来自网络套接字的数据。这样的操作将导致 CPU 核资源空闲，降低并行处理等级，进而使性能无法达到最佳。
- 不能在任务内部抛出校验异常，必须编写代码来处理异常。对于未校验异常有一种特殊的处理方式。



### Fork/Join 框架的组件

Fork/Join 框架包括四个基本类。

- **ForkJoinPool 类**

  实现了 Executor 接口和 ExecutorService 接口，执行 Fork/Join 任务时将用到 Executor 接口。

  可以指定并行处理的等级（运行并行线程的最大数目）。默认情况下，它将可用处理器的数目作为并发处理等级。

- **ForkJoinTask 类**

  所有 Fork/Join 任务的基本抽象类，提供了 `fork()` 方法和 `join()` 方法，以及这些方法的一些变体。

  该类还实现了 Future 接口，提供了一些方法来判断任务是否以正常方式结束，它是否被撤销，或者是否抛出了一个未校验异常。 

  RecursiveTask 类、RecursiveAction 类和 CountedCompleter 类提供了 `compute()` 抽象方法。为了执行实际的计算任务，该方法应该在子类中实现。

- **RecursiveTask 类**

  该类扩展了 ForkJoinTask 类。RecursiveTask 也是一个抽象类，应该作为实现返回结果的 Fork/Join 任务的起点。

- **RecursiveAction 类**

  该类扩展了 ForkJoinTask 类。RecursiveAction 类也是一个抽象类，应该作为实现不返回结果的 Fork/Join 任务的起点。

- **CountedCompleter 类**

  该类扩展了 ForkJoinTask 类。该类应作为实现任务完成时触发另 一任务的起点。



## k-means 聚类

k-means 聚类算法将预先未分类的项集分组到预定的 K 个簇。

每一项通常都由一个特征（属性）向量（作为一个数学概念而非数据结构） 定义。所有项都有相同数目的属性。每个簇也由一个含有同样属性数目的向量定义，这些属性描述了所有可分类到该簇的项。该向量叫作 centroid。例如，如果这些项是用数值型向量定义的，那么簇就定义为划分到该簇的各项的平均值。

该算法基本上可以分为四个步骤。

- **初始化**：在第一步中，要创建最初代表 K 个簇的向量，通常可以随机初始化这些向量。
- **指派**：将每一项划分到一个簇中。为了选择该簇，可以计算项和每个簇之间的距离。可以使用欧氏距离作为距离度量方式，计算代表项的向量和代表簇的向量之间的距离。之后将该项分配到与其距离最短的簇中。
- **更新**：一旦对所有项进行分类之后，必须重新计算定义每个簇的向量。通常要计算划分到该簇所有项的向量的平均值。
- **结束**：检查是否有些项改变了为其指派的簇。如果存在变化，需要再次转入指派步骤。否则算法结束，所有项都已分类完毕。



### ★ 小结

Task 的 `compute()` 方法中，判断进行逻辑处理，还是继续拆分 Task。拆分的两个 Task 通过 `invokeAll(task1, task2)` 方法执行。

使用 ForkJoinPool 的 `execute()` 方法以异步方式执行池中的任务，并且使用 Task 对象的 `join()` 方法等待其结束。

最后 ForkJoinPool 对象使用 `shutdown()` 方法以结束其执行。



## 数据筛选

### TaskManager

任务管理器，用来控制任务的撤销。使用 AtomicBoolean 变量确保所有任务都能以线程安全的方式访问它们的值。

```java
public void cancelTasks(RecursiveTask<?> sourceTask) {
    if (cancelled.compareAndSet(false, true)) {
        for (RecursiveTask<?> task : tasks) {
            if (task != sourceTask) {
                if (cancelled.get()) {
                    task.cancel(true);
                } else {
                    tasks.add(task);
                }
            }
        }
    }
}
```

`compareAndSet(false, true)` 方法将 AtomicBoolean 变量设置为 true，而且当且仅当该变量的当前值为 false 时才会返回 true 值。

### IndividualTask

用于实现 `findAny()` 方法的 RecursiveTask 类。

如果任务需要处理的元素数比 size 属性值小，该方法直接进行对象查找。

如果该方法找到了想要的对象，那么它将返回该对象并且使用 `cancelTasks()` 方法撤销剩余任务的执行。如果该方法没有找到想要的对象，那么它将返回 null 值。



如果要处理的项数要比 size 属性规定的多，那么要创建两个子任务来分别处理其中的一半元素。然后，向任务管理器添加新创建的任务，并且删除实际任务。如果要撤销任务，即指仅撤销正在运行的任务。

使用 `fork()` 方法以异步方式将任务发送给 ForkJoinPool，并且使用 `quietlyJoin()` 方法等待其执行结束。
`join()` 方法和 `quietlyJoin()` 方法之间的区别在于，`join()` 启动之后，如果任务撤销，将抛出异常，或者在方法内部抛出一个未校验异常，而 `quietlyJoin()` 方法则不抛出任何异常。

然后，从 TaskManager 类中删除子任务。使用 `join()` 方法获取任务的结果。

```java
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
            CensusData res = task1.join();
            if (res != null) {
                return res;
            }
            manager.deleteTask(task1);
        } catch (CancellationException ignored) {
        }
        try {
            CensusData res = task2.join();
            if (res != null) {
                return res;
            }
            manager.deleteTask(task2);
        } catch (CancellationException ignored) {
        }
    }
    return null;
}
```



### ListTask

用于实现 `findAll()` 方法的 RecursiveTask 类，逻辑与 IndividualTask 类似。

```java
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
```



### ★ 小结

`compute()` 方法拆分 Task 的部分也需要获得结果时：

1. `fork()`: 提交任务。
2. `quietlyJoin()`: 等待执行结束。
3. `join()`: 获取任务的结果。



## 归并排序

归并排序通常使用分治方法实现，为实现归并排序算法，将未排序的列表划分为仅有一个元素的子列表。然后，将这些未排序的子列表合并以产生排序后的子列表，直到将所有这些子列表处理完毕。



### MergeSortTask

如果起始索引和终止索引之间的差距大于或等于 1024，那么使用 `compute()` 方法，将任务分割成两个子任务来分别处理原集合的两个子集。两个任务采用 `fork()` 方法以异步方式将任务发送给 ForkJoinPool。

否则，执行 `SerialMergeSorg.mergeSort()` 对数组进行排序，然后调用 `tryComplete()` 方法。子任务执行完毕之后，该方法将从内部调用 `onCompletion()` 方法。

```java
public class MergeSortTask extends CountedCompleter<Void> {

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
```



## Fork/Join 框架的其他方法

将任务发送给池：

- `execute()`

  将任务发送给 ForkJoinPool 之后立即返回。

- `invoke()`

  将任务发送给 ForkJoinPool 后，当任务完成执行后方可返回。

- `submit()`

  将任务发送给 ForkJoinPool 之后立即返回一个 Future 对象，用以控制任务的状态并且获得其结果。

ForkJoinTask 类为 `invoke()` 方法提供了一种替代方案，即 `quietlyInvoke()` 方法。这两种方法的主要区别在于，`invoke()` 方法返回任务执行的结果或者在必要时抛出异常，而 `quietlyInvoke()` 方法不返回任务的结果，也不抛出任何异常。

ForkJoinTask 类提供了 `get(long timeout, TimeUnit unit)` 方法来获取某个任务返回的结果。该方法在参数中指定了等待任务结果的时间周期。如果该任务在这一时间周期结束之前完成了执行，则该方法返回相应结果。否则，该方法抛出一个 TimeoutException 异常。


