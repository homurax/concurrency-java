# 从任务获取数据：Callable 与 Future 接口

执行器框架也允许执行其他基于 Callable 接口和 Future 接口返回值的任务。

Callable 是一种函数接口，它定义了 `call()` 方法。`call()` 方法可以抛出一种与 Runnable 接口不同的校验异常。

Callable 接口的处理结果要用 Future 接口来包装，而 Future 接口则描述了异步计算的结果。



## Callable 和 Future 接口简介

在执行器中，你可以执行两种任务。

- **基于 Runnable 接口的任务**

  这些任务实现了不返回任何结果的 `run()` 方法。

- **基于 Callable 接口的任务**

  这些任务实现了返回某个对象作为结果的 `call()` 方法。返回的具体类型由 Callable 接口的泛型参数指定。为了获取该任务返回的结果，执行器会为每个任务返回一个 Future 接口的实现。

  

### Callable 接口

Callable 接口的主要特征如下。

- 有一个简单类型参数，与 `call()` 方法的返回类型相对应。
- 声明了 `call()` 方法。执行器运行任务时，该方法会被执行器执行。它必须返回声明中指定类型的对象。
- `call()` 方法可以抛出任何一种校验异常。可以实现自己的执行器并重载 `afterExecute()` 方法来处理这些异常。



### Future 接口

Future 接口的实现可以用来控制任务的执行和任务状态，以及能够获取结果。该接口的主要特征如下。

- 可以使用 `cancel()` 方法来撤销任务的执行。该方法有一个布尔型参数，用于指定是否需要在任务运行期间中断任务。

- 可以校验任务是否已被撤销（`isCancelled()`）或者是否已经结束（`isDone() `）。

- 可以使用 `get()` 方法获取任务返回的值。该方法有两个重载。

  第一个变体不带有参数，当任务完成执行后，该变体将返回任务所返回的值。如果任务并没有完成执行，它将挂起执行线程直到任务执行完毕。

  第二个变体带有两个参数：时间周期和该周期的 TimeUnit（时间单位）。区别在于将线程等待的时间周期作为参数来传递。如果这一周期结束后任务仍未结束执行，该方法就会抛出一个 TimeoutException 异常。



## 单词最佳匹配算法

用于评估两个单词之间相似度的指标：使用 Levenshtein 距离来度量两个字符序列的差异。

Levenshtein 距离是指，将第一个字符串转换成第二个字符串所需进行的最少的插入、删除或替换操作次数。

### ★ 小结

`invokeAny()` ：有任意一个 Task 成功执行了（无异常），则返回成功完成的 Task 的结果。正常或者异常返回时，尚未完成的 Task 会被取消。

所以 Task 类实现 Callable 接口，`run()` 方法中在指定的范围内查询是否存在目标 string，存在则返回 true，否则抛出异常。

`submit()` 接收一 个 Callable 对象作为参数，并立即返回一个 Future 对象。

`invokeAll()` 方法接收一个 Callable 对象集合作为参数，并且返回一个 Future 对象集合。Future 与 Callable  按照顺序关联。`invokeAll()` 方法仅当所有 Callable 任务都终止执行时才返回。



## 为文档集创建倒排索引

### 每个文档一个任务

如果为每个文档都发送一个任务，可以以如下方式处理结果。

- 在发送每个任务后，显然这是不现实的。
- 在所有任务完成后，这样就需要存储大量 Future 对象。
- 在发送一组任务后，需要编写代码来同步两个操作。

这些方法都有一个问题：以顺序方式来处理这些任务的结果。如果使用 `invokeAll()` 方法， 所处的情形就与第二点相似，必须等所有任务都结束。

---

一个可行的供选方案是创建其他一些任务来处理与每个任务相关的 Future 对象，而 Java 并发 API 提供了一种很好的解决方案，采用 CompletionService 接口及其实现 ExecutorCompletionService 类来实现这一解决案。

CompletionService 对象带有一个执行器，它允许将任务生成和那些任务结果的使用分离开来。可以使用 `submit()` 方法向执行器发送任务，并在这些任务执行完毕后使用 `poll()` 或者 `take()` 方法来获取其结果。

**Usage Examples.**

> Suppose you have a set of solvers for a certain problem, each returning a value of some type `Result`, and would like to run them concurrently, processing the results of each of them that return a non-null value, in some method `use(Result r)`. You could write this as:

```java
void solve(Executor e, Collection<Callable<Result>> solvers)
        throws InterruptedException, ExecutionException {

    CompletionService<Result> ecs = new ExecutorCompletionService<Result>(e);
    for (Callable<Result> s : solvers) {
        ecs.submit(s);
    }
    int n = solvers.size();
    for (int i = 0; i < n; ++i) {
        Result r = ecs.take().get();
        if (r != null) {
            use(r);
        }
    }
}
```

> Suppose instead that you would like to use the first non-null result of the set of tasks, ignoring any that encounter exceptions, and cancelling all other tasks when the first one is ready:

```java
void solve(Executor e, Collection<Callable<Result>> solvers)
        throws InterruptedException {

    CompletionService<Result> ecs = new ExecutorCompletionService<Result>(e);
    int n = solvers.size();
    List<Future<Result>> futures = new ArrayList<Future<Result>>(n);
    Result result = null;
    try {
        for (Callable<Result> s : solvers) {
            futures.add(ecs.submit(s));
        }
        for (int i = 0; i < n; ++i) {
            try {
                Result r = ecs.take().get();
                if (r != null) {
                    result = r;
                    break;
                }
            } catch (ExecutionException ignore) {
            }
        }
    } finally {
        for (Future<Result> f : futures) {
            f.cancel(true);
        }
    }

    if (result != null) {
        use(result);
    }
}
```



### ★ 小结

- 为每个文档分配一个任务以解析文档并且生成其词汇表，而该任务将由 CompletionService 对象来执行。这些任务都在 IndexingTask 类中实现。

- 创建两个线程来处理任务结果并且构造倒排索引。这些任务都在 InvertedIndexTask 类中实现。

  `run()` 方法中使用来自 CompletionService 类的 ` take()` 方法获取与某一任务相关联的 Future 对象，在线程中断之前该循环将一直运行。当该线程中断之后，再次使用 `poll()` 方法处理所有待处理的 Future 对象。

启动所有要素之后，使用 `shutdown()` 和 `awaitTermination()` 等待执行器结束。随后中断 InvertedIndexTask 线程，等待线程结束。



## 其他相关方法

AbstractExecutorService 接口：

- `invokeAll (Collection> tasks, long timeout, TimeUnit unit)`

  当作为参数传递的 Callable 任务列表中的所有任务完成执行，或者执行时间超出了第二、第三个参数指定的时间范围时，该方法返回一个与该 Callable 任务列表相关联的 Future 对象列表。

- `invokeAny (Collection> tasks, long timeout, TimeUnit unit)`

  当作为参数传递的 Callable 任务列表中的任务在超时之前完成其执行并且没有抛出异常时，该方法返回 Callable 任务列表中第一个任务的结果。如果超时，那么该方法抛出一个 TimeoutException 异常。



CompletionService 接口：

- `poll()`

  从内部数据结构中检索并且删除自上一次调用 `poll()` 或 `take()` 方法以来下一个已完成任务的 Future 对象。如果没有任何任务完成，执行该方法将返回 null 值。

- `take()`

  如果没有任何任务完成，它将休眠该线程， 直到有一个任务执行完毕为止。

