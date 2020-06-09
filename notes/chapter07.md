# 优化分治解决方案：Fork/Join 框架

Java 7 并发 API 通过 Fork/Join 框架引入了一种特殊的执行器。该框架的设计目的是针对那些可以使用分治设计范式来解决的问题，实现最优的并发解决方案。

## Fork/Join 框架简介

该框架基于 ForkJoinPool 类，该类是一种特殊的执行器，具有 `fork()` 方法和 `join()` 方法两 个操作（以及它们的不同变体），以及一个被称作工作窃取算法的内部算法。



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

- **`fork()` **：该方法可以将一个子任务发送给 Fork/Join 执行器。
- **`join()`** ：该方法可以等待一个子任务执行结束后返回其结果。

Fork/Join 框架还有另一个关键特性，即工作窃取算法。该算法确定要执行的任务。当一个任务使用 `join()` 方法等待某个子任务结束时，执行该任务的线程将会从任务池中选取另一个等待执行的任务并且开始执行。通过这种方式，Fork/Join 执行器的线程总是通过改进应用程序的性能来执行任务。

可以通过调用静态方法 `ForkJoinPool.commonPool()` 获得公用池，而不需要采用显式方法创建。这种默认的 Fork/Join 执行器会自动使用由计算机的可用处理器确定的线程数。可以通过更改系统属性值 `java.util.concurrent.ForkJoinPool.common.parallelism` 来修改这一默认行为。



### Fork/Join 框架的局限性

- 不再进行细分的基本问题的规模既不能过大也不能过小。按照 Java API 文档的说明，该基本问题的规模应该介于 100 到 10000 个基本计算步骤之间。
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



## k-means 聚类算法

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











