# 线程

实现一个线程可以继承 Thread 类，实际从灵活性考虑，更多的是去编写自己的逻辑 Task 类，去实现 Runnable 接口（`run()`）。如果需要获取任务的返回值，就实现 Callable 接口（`call()`），泛型指定为返回类型，配合执行器使用。



Thread 具有一系列设置 / 获取特征状态的方法。

Thread 常用方法：

- `thread.start()`

  启动一个线程。

- `Thread.interrupted()`

  判断当前线程是否已被中断。

- `Thread.sleep()`

  将线程的执行暂停一段时间。

- `thread.join()`

  让调用方去等待被调用的线程执行结束。



`Runtime.getRuntime().availableProcessors()` ：获取 JVM 可用处理器数。



## 并发设计

自定义 Task 类实现 Runnable 接口，`run()` 方法中编写核心计算逻辑。

循环生成 Task 实例，创建 Thread 并调用 `start()` 方法启动线程。

循环 Thread，调用 `join()` 方法，等待所有线程执行完毕。

如果多个线程中涉及操作同一个容器，需要注意是否存在竞争。

可以通过限定每个线程操作不同的区间来避免竞争，也可以直接使用并发容器。



# 执行器

## 基本组件

- **Executor 接口**

  Executor 框架的基本接口。它仅定义了一个方法，即允许编程人员向执行器发送一个 Runnable 对象。

- **ExecutorService 接口**

  继承了 Executor 接口并且包括更多方法，增加了该框架的功能。

  - 执行可返回结果的任务。
  - 通过单个方法调用执行一个任务列表。
  - 结束执行器的执行并且等待其终止。

- **ThreadPoolExecutor 类**

  实现了 Executor 接口和 ExecutorService 接口。此外，它还包含一些其他获取执行器状态的方法、确定执行器参数的方法，以及支持继承和调整其功能的方法。

- **Executors 类**

  为创建 Executor 对象和其他相关类提供了实用方法。

```
ThreadPoolExecutor -----> AbstractExecutorService - - - - -> ExecutorService -----> Executor
```



## 自定义执行器

继承 ThreadPoolExecutor 实现自定义执行器。

- `beforeExecute()`

  该方法在执行器中的某一并发任务执行之前被调用。

  它接收将要执行的 Runnable 对象和将要执行这些对象的 Thread 对象。该方法接收的 Runnable 对象是 FutureTask 类的一个实例，而不是使用 `submit()` 方法发送给执行器的 Runnable 对象。

- `afterExecute()`

  该方法在执行器中的某一并发任务执行之后被调用。

  它接收的是已执行的 Runnable 对象和一个 Throwable 对象，该 Throwable 对象存储了任务中可能抛出的异 常。Runnable 对象是 FutureTask 类的一个实例。

- `newTaskFor()`

  基于 `submit()` 发送的 Callable 对象创建一个 FutureTask。

  该方法必须返回 RunnableFuture 接口的一个实现。默认情况下，返回 FutureTask 类的一个实例。



可以在执行器创建之时更改一些参数以改变其行为。

- BlockingQueue

  每个执行器均使用一个内部的 BlockingQueue 存储等待执行的任务。可以将该接口的任何实现作为参数传递。例如，更改执行器执行任务的默认顺序。

- ThreadFactory

  可以指定 ThreadFactory 接口的一个实现，而且执行器将使用该工厂创建执行该任务的线程。例如，可以使用 ThreadFactory 接口创建 Thread 类的一个继承类，保存有关任务执行时间的日志信息。

- RejectedExecutionHandler

  调用 `shutdown()` 方法或者 `shutdownNow()` 方法之后，所有发送给执行器的任务都将被拒绝。可以指定 RejectedExecutionHandler 接口的一个实现管理这种情形



## 执行任务

- **基于 Runnable 接口的任务**

  这些任务实现了不返回任何结果的 `run()` 方法。

- **基于 Callable 接口的任务**

  这些任务实现了返回某个对象作为结果的 `call()` 方法。返回的具体类型由 Callable 接口的泛型参数指定。为了获取该任务返回的结果，执行器会为每个任务返回一个 Future 接口的实现。

---

- `execute()` 

  将 Runnable 发送个到执行器中，无返回值。

- `submit()`

  将 Callable 提交给执行器，立即返回 Future。

- `invokeAny()`

  将 `Collection<Callable>`  提交给执行器。有任意一个 Task 成功执行了（无异常），则返回成功完成的 Task 的结果。正常或者异常返回时，尚未完成的 Task 会被取消。

- `invokeAll()`

  将 `Collection<Callable>`  提交给执行器。返回用于获取执行结果的 `List<Future>`，Future 与 Callable 按照顺序关联。方法仅当所有 Callable 任务都终止执行时才返回。



## 撤销、获取结果

Future 接口描述了异步计算的结果。

- `cancel()`

  撤销任务的执行。该方法有一个布尔型参数，用于指定是否需要在任务运行期间中断任务。

- `isCancelled()`

  校验任务是否已被撤销。

- `isDone() `

  校验任务是否已经结束。

- `get()`

  获取任务返回的值。如果任务并没有完成执行，它将挂起执行线程直到任务执行完毕。



## 停止执行器

- `shutdown()` 

  被调用后不再接受新任务，已经接收的任务仍会执行，等待执行器中所有处于等待状态的任务全部终结。

- `shutdownNow()` 

  被调用后尝试停止所有正在执行的任务，暂停正在等待的任务的处理，并返回正在等待执行的任务的 List。

- `awaitTermination(long timeout, TimeUnit unit)`

  调用后阻塞线程，直到调用 `shutdown()` 或者 `shutdownNow()` 之后所有任务完成，或者发生超时，或者当前线程被中断。



## ExecutorCompletionService 

采用 CompletionService 接口及其实现 ExecutorCompletionService 类，可以创建其他一些任务处理与每个任务相关的 Future 对象。

CompletionService 对象带有一个执行器，它允许将任务生成和那些任务结果的使用分离开来。可以使用 `submit()` 方法向执行器发送任务，并在这些任务执行完毕后使用 `poll()` 或者 `take()` 方法来获取其结果。

- `poll()`

  从内部数据结构中检索并且删除自上一次调用 `poll()` 或 `take()` 方法以来下一个已完成任务的 Future 对象。如果没有任何任务完成，执行该方法将返回 null 值。

- `take()`

  如果没有任何任务完成，它将休眠该线程， 直到有一个任务执行完毕为止。



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



## 并发设计

使用执行器可以不用创建 Thread ，而是实例化 Task 后直接提交到执行器去执行。

选择适合业务的提交方法，以及获取执行结果。

可以通过继承、重写执行器来添加自己业务所需的功能。

---

对于执行大量的任务，如果处理结果，无非以下几种方式：

- 在发送每个任务后，显然这是不现实的。
- 在所有任务完成后，这样就需要存储大量 Future 对象。
- 在发送一组任务后，需要编写代码来同步两个操作。

这些方法都有一个问题：以顺序方式来处理这些任务的结果。如果使用 `invokeAll()` 方法， 所处的情形就与第二点相似，必须等所有任务都结束。



使用 ExecutorCompletionService，可以在多个 Task 的 `run()` 方法中使用 `take()` 方法获取与某一任务相关联的 Future 对象，在线程中断之前该循环将一直运行。当 ExecutorCompletionService 持有的执行器停止后，中断这些处理结果的 Task。线程中断之后，再次使用 `poll()` 方法处理所有待处理的 Future 对象。



# Fork/Join

## 基本特征

Fork/Join 框架基于 ForkJoinPool 类，是一种特殊的执行器，具有 `fork()` 方法和 `join()` 方法两个操作，以及一个被称作工作窃取算法的内部算法。

当一个任务使用 `join()` 方法等待某个子任务结束时，执行该任务的线程将会从任务池中选取另一个等待执行的任务并且开始执行。通过这种方式，Fork/Join 执行器的线程总是通过改进应用程序的性能来执行任务。

`ForkJoinPool.commonPool()` 可以获得公用池，不需要采用显式方法创建。这种默认的 Fork/Join 执行器会自动使用由计算机的可用处理器确定的线程数。

**ForkJoinPool**

- `execute()`

  将任务发送给 ForkJoinPool 之后立即返回。

- `submit()`

  将任务发送给 ForkJoinPool 之后立即返回一个 Future 对象，用以控制任务的状态并且获得其结果。

- `invoke()`

  将任务发送给 ForkJoinPool 后，当任务完成执行后方可返回结果。

- `invokeAll()`

  将任务发送给 ForkJoinPool 后，当全部任务完成执行后方可返回 `List<Future >`。



**ForkJoinTask**

- `fork()`

  将一个子任务发送给 ForkJoinPool 执行器。

- `join()`

  等待一个子任务执行结束后返回其结果。

- `quietlyJoin()`

  不返回任务的结果，也不抛出任何异常。

- `invoke()`

  将任务发送给 ForkJoinPool 后，当任务完成执行后方可返回。

- `quietlyInvoke()`

  不返回任务的结果，也不抛出任何异常。



## 局限性

- 不再进行细分的基本问题的规模既不能过大也不能过小。按照 Java API 文档的说明，该基本问题的规模应该介于 **100 到 10000** 个基本计算步骤之间。
- 数据可用前，不应使用阻塞型 I/O 操作，例如读取用户输入或者来自网络套接字的数据。这样的操作将导致 CPU 核资源空闲，降低并行处理等级，进而使性能无法达到最佳。
- 不能在任务内部抛出校验异常，必须编写代码来处理异常。对于未校验异常有一种特殊的处理方式。



## 框架的组件

- **ForkJoinPool 类**

  实现了 Executor 接口和 ExecutorService 接口，执行 Fork/Join 任务时将用到 Executor 接口。

  可以指定并行处理的等级（运行并行线程的最大数目）。默认情况下，它将可用处理器的数目作为并发处理等级。

- **ForkJoinTask 类**

  所有 Fork/Join 任务的基本抽象类，提供了 `fork()` 方法和 `join()` 方法，以及这些方法的一些重载。

  该类还实现了 Future 接口，提供了一些方法来判断任务是否以正常方式结束，它是否被撤销，或者是否抛出了一个未校验异常。

  RecursiveTask 类、RecursiveAction 类和 CountedCompleter 类提供了 `compute()` 抽象方法。为了执行实际的计算任务，该方法应该在子类中实现。

- **RecursiveTask 类**

  该类继承了 ForkJoinTask 类。RecursiveTask 也是一个抽象类，应该作为实现返回结果的 Fork/Join 任务的起点。

- **RecursiveAction 类**

  该类继承了 ForkJoinTask 类。RecursiveAction 类也是一个抽象类，应该作为实现不返回结果的 Fork/Join 任务的起点。

- **CountedCompleter 类**

  该类继承了 ForkJoinTask 类。该类应作为实现任务完成时触发另 一任务的起点。



## 并发设计

不需要返回结果时：

Task 继承 RecursiveAction。`compute()` 中，判断进行逻辑处理，还是继续拆分 Task。拆分的两个 Task 通过 `invokeAll(task1, task2)` 执行。

Task 使用 `join()` 方法等待其结束。



需要返回结果时：

Task 继承 RecursiveTask。拆分 Task 部分：

1. `fork()`: 提交任务。
2. `quietlyJoin()`: 等待执行结束。
3. `join()`: 获取任务的结果。



需要子任务完成执行后执行某个方法时：

Task 继承 CountedCompleter。重写 `compute()` 和 `onCompletion()` 方法。 `compute()` 中调用 `tryComplete()` 方法。



如果使用的不是公用池，最后使用 `shutdown()` 方法以结束其执行。



# 同步机制

Java 语言提供的最基本的同步机制是 synchronized 关键字。该关键字可应用于某个方法或者某个代码块。对于第一种情况，一次只有一个线程可以执行该方法。对于第二种情况，要指定一个对某个对象的引用。在这种情况下，同时只能执行被某一对象保护的一个代码块。



- Lock 接口及其实现类

  该机制允许实现一个临界段，保证只有一个线程执行该代码块。

- Semaphore

  实现了由 Edsger Dijkstra 提出的著名的信号量同步机制。

- CountDownLatch

  允许实现这样的场景：一个或多个线程等待其他线程结束。

- CyclicBarrier

  允许将不同的任务同步到某个共同的节点。

- Phaser

  允许分为多个阶段实现并发任务。

- Exchanger

  允许在两个线程之间实现一个数据交换点。

- CompletableFuture

  它扩展了执行器任务的 Future 机制，以一种异步方式生成任务的结果。可以指定任务在结果生成之后执行，这样就可以控制任务的执行顺序。



## Semaphore

信号量机制是 Edsger Dijkstra 于 1962 年提出的，用于控制对一个或多个共享资源的访问。

该机制基于一个内部计数器以及两个名为 `wait()` 和 `signal()` 的方法。

当一个线程调用了 `wait()` 方法时，如果内部计数器的值大于 0，那么信号量对内部计数器做递减操作，并且该线程获得对该共享资源的访问。

如果内部计数器的值为 0，那么线程将被阻塞，直到某个线程调用 `singal()` 方法为止。

当一个线程调用了 `signal()` 方法时，信号量将会检查是否有某些线程处于等待状态（它们已经调用了 `wait()` 方法）。

如果没有线程等待，它将对内部计数器做递增操作。如果有线程在等待信号量，就获取这其中的一个线程，该线程的 `wait()` 方法结束返回并且访问共享资源。

其他线程将继续等待，直到轮到自己为止。

在 Java 中，信号量在 Semaphore 类中实现。`wait()` 方法被称作 `acquire()`，而 `signal()` 方法被称作 `release()` 。

```java
public void run() {
    try {
        semaphore.acquire();
        CommonTask.doTask();
    } catch (InterruptedException e) {
        e.printStackTrace();
    } finally {
        semaphore.release();
    }
}
```



## CountDownLatch

该类提供了一种等待一个或多个并发任务完成的机制。它有一个内部计数器，必须使用要等待的 任务数初始化。

`await()` 方法休眠调用线程，直到内部计数器为 0，并且使用 `countDown()` 方法对该内部计数器做递减操作。



Task 中完成任务后调用 `countDown()`，主线程中调用 `await()` 等待，从而实现主线程中阻塞到所有 Task 完成后继续进行。

Task 中任务开始前调用 `await()` 等待，主线程中调用 `countDown()`，从而实现主线程中控制所有 Task 同时执行。



## CyclicBarrier

该类允许将一些任务同步到某个共同点。所有的任务都在该点等待，直到任务全部到达该点为止。

从内部来看，该类还管理了一个内部计数器，用于记录尚未到达该点的任务。

当一个任务到达指定点时，它要执行 `await()` 方法以等待其他任务。

当所有任务都到达时，CyclicBarrier 对象将它们唤醒，这样就能够继续执行。

当所有的参与方都到达后，该类允许执行另一个任务。为了实现这一点，要在该对象的构造方法中指定一个 Runnable 对象。

```java
public CyclicBarrier(int parties, Runnable barrierAction)
```

当 parties 个 Task 中调用了 `barrier.await()` 后，就会执行 barrierAction 。



## Phaser

Phaser 用于控制以并发方式划分为多个阶段的算法的执行。如果处理过程已有明确定义的步骤，那么必须在开始第二个步骤之前完成第一步的工作，以此类推，并且可以使用 Phaser 类实现该过程的并发版本。Phaser 类的主要特征有以下几点。

- phaser 必须知道要控制的任务数。Java 称之为参与者的注册机制。参与者可以随时在分段器中注册。
- 任务完成一个阶段之后必须通知分段器。在所有参与者都完成该阶段之前，分段器将使该任务处于休眠状态。
- 分段器在内部保存了一个整数值，该值存储分段器已经进行的阶段变更数目。
- 参与者可以随时脱离分段器的控制。Java 将这一过程称为参与者的注销。
- 当分段器做出阶段变更时，可以执行定制的代码。
- 控制分段器的终止。如果一个分段器终止了，就不再接受新的参与者，也不会进行任务之间的同步。
- 通过一些方法获得分段器的参与者数目及其状态。



### 参与者的注册与注销

正常情况下，参与者在执行开始时注册，但是也可以随时注册。

- 创建 Phaser 对象时：Phaser 类提供了四个不同的构造方法。其中常用的有两个。
  - `Phaser()`：该构造方法创建了一个 0 个参与者的分段器。
  - `Phaser(int parties)`：该构造方法创建了一个含有给定数目参与者的分段器。
- 通过下述方法显式创建。
  - `bulkRegisterl(int parties)`：同时注册给定数目的新参与者。
  - `register()`：注册一个新参与者。

分段器控制的任务完成执行时，必须从分段器注销。如果不这样做，分段器就会在下一阶段变更中一直等待该任务。

注销一个参与者，可以使用 `arriveAndDeregister()` 方法。使用该方法告知分段器该任务已经完成了当前阶段，而且不再参与下一阶段。



### 同步阶段变更

分段器的主要目的是使那些可以分割成多个阶段的算法以并发方式执行。所有任务完成当前阶段之前，任何任务都不能进入下一阶段。

Phaser 类提供了 `arrive()`、 `arriveAndDeregister()` 和 `arriveAndAwaitAdvance()` 三个方法通报任务已经完成当前阶段。

如果其中某个任务没有调用上述三个方法之一，那么分段器对其他参与任务的阻塞是不确定的。继续进入下一阶段需要用到下述方法。

- `arriveAndAwaitAdvance()`

  任务使用该方法向分段器通报，表明它已经完成了当前阶段并且要继续下一阶段。分段器将阻塞该任务，直到所有参与的任务已调用其中一个同步方法。

- `awaitAdvance(int phase)`

  任务使用该方法向分段器通报，如果该方法参数中的数值和分段器的实际阶段数相等，就要等待当前阶段结束；如果这两个数值不相等，则该方法立即返回。

  

### 其他功能

在所有参与任务都完成了某个阶段的执行之后，在继续下一阶段之前，Phaser 类执行 `onAdvance()` 方法。该方法接收如下两个参数。

- phase：这是已执行完毕阶段的编号。第一个阶段的编号为 0。
- registeredParties：这个参数代表参与任务的数目。

如果想在两个阶段之间执行一些代码，例如，对某些数据进行排序或者转换，那么可以扩展 Phaser 类并重载该方法以实现自己的分段器。



分段器可以有以下两种状态。

- **激活状态**

  创建了分段器且新的参与者注册后，分段器将进入激活状态，并持续这种状态，直到其终止。处于这种状态时，它接受新的参与者并像之前所述那样工作。

- **终止状态**

  `onAdvance()` 方法返回 true 值时，分段器进入这种状态。默认情况下，当所有参与者都注销后，`onAdvance()` 方法将返回 true 值。



Phaser 类提供了一些方法，获取分段器状态和其中参与者的信息。

- `getRegisteredParties()`：分段器中参与者的数目。
- `getPhase()`：当前阶段的编号。
- `getArrivedParties()`：已经完成当前阶段的参与者的数目。
- `getUnarrivedParties()`：尚未完成当前阶段的参与者的数目。
- `isTerminated()`：如果分段器处于终止状态，则该方法返回 true 值，否则返回 false 值。



### 并发设计

Phaser 的主要目的是为执行分为多阶段的算法的任务提供同步。在所有任务都完成上一阶段之前，任何任务都不能开始执行下一阶段。

分段器必须知道任务要进行同步的任务数量。必须使用构造方法、`bulkRegister()` 方法或 `register()` 方法在分段器中注册任务。

分段器可以以不同方式同步任务。

最常见的方式是使用 `arriveAndAwaitAdvance()` 方法告诉分段器，任务已经完成了一个阶段的执行，要继续执行下一阶段。该方法将休眠该线程直到剩下的任务都完成当前阶段为止。

`arrive()` 方法用于通知分段器当前阶段已经完成，但是不会等待剩下的任务。

`arriveAndDeregister()` 方法用于告知分段器当前阶段已经完成，而且并不想在分段器中继续等待，通常是因为已经完成了任务。

`awaitAdvance()` 方法可用于等待指定阶段结束。



## CompletableFuture

这是在 Java 8 并发 API 中引入的一种同步机制，在 Java 9 中又有了一些新方法。

它扩展了 Future 机制，为其赋予了更强的功能和更大的灵活性。它允许实现一个事件驱动的模型，链接那些只有当其他任务执行完毕后才执行的任务。

与 Future 接口相同，CompletableFuture 也必须采用操作要返回的结果类型进行参数化。

和 Future 对象一样，CompletableFuture 类表示的是异步计算的结果，只不过 CompletableFuture 的结果可以由任意线程确立。

当计算正常结束时，该类采用 `complete()` 方法确定结果，而当计算出现异常时，则采用 `completeExceptionally()` 方法。

如果两个或者多个线程调用同一 CompletableFuture 的 `complete()`方法或 `completeExceptionally()` 方法，那么只有第一个调用会起作用。

除了 `complete()` 方法，也可以使用 `runAsync()` 方法或者 `supplyAsync()` 创建一个任务结果。

`runAsync()` 方法执行一个 Runnable 对象并且返回 `CompletableFuture`，这样计算就不能再返回任何结果了。

`supplyAsync()` 方法执行了 Supplier 接口的一个实现，它采用本次计算要返回的类型进行参数化。

该 Supplier 接口提供了 `get()` 方法。在该方法中，需要包含任务代码并且返回任务生成的结果。



该类提供了大量方法，允许通过实现一个事件驱动的模型组织任务的执行顺序，一个任务只有在其之前的任务完成之后才会开始。这其中包括如下方法。

- `thenApplyAsync()`

  方法接收 Function 接口的一个实现作为参数。该函数将在调用 CompletableFuture 完成后执行。

  方法将返回 CompletableFuture 以获得 Fuction 的结果。

- `thenComposeAsync()`

  方法和 `thenApplyAsync()` 方法相似，但是当供给函数也返回 CompletableFuture 时很有用。

- `thenAcceptAsync()`

  方法和前一个方法相似，只不过其参数是 Consumer 接口的一个实现，计算不会返回结果。

- `thenRunAsync()`

  方法和前一个等价，只不过在这种情况下接收一个 Runnable 对象作为参数。

- `thenCombineAsync()`

  该方法接收两个参数。

  第一个参数为另一个 CompletableFuture 实例，另一个参数是 BiFunction 接口的一个实现。

  该 BiFunction 接口实现将在两个 CompletableFuture（当前调用的和参数中的）都完成后执行。

  该方法将返回 CompletableFuture 以获取 BiFunction 的结果。

- `runAfterBothAsync()`

  方法接收两个参数。

  第一个参数为另一个CompletableFuture，而第二个参数为 Runnable 接口的一个实现，它将在两个 CompletableFuture（当前调用的和参数中的）都完成后执行。

- `runAfterEitherAsync()`

  方法与前一个方法等价，只不过当其中一个 CompletableFuture 对象完成之后才会执行 Runnable 任务。

- `allOf()`

  方法接收 CompletableFuture 对象的一个变量列表作为参数。

  它将返回一个 `CompletableFuture` 对象，而该对象将在所有的 CompletableFuture 对象都完成之后返回其结果。

- `anyOf()`

  方法和前一个方法等价，只是返回的 CompletableFuture 对象会在其中一个 CompletableFuture 对象完成之后返回其结果。

最后，如果想要获取 CompletableFuture 返回的结果，可以使用 `get()` 方法或者 `join()`方法。这两个方法都会阻塞调用线程，直到 CompletableFuture 完成之后返回其结果。

这两个方法之间的主要区别在于，`get()` 方法抛出 ExecutionException（一个校验异常），而 `join()` 方法抛出 RuntimeException（一个未校验异常）。

因此，在不抛出异常的 lambda 内部，使用 `join()` 方法更为方便。

带有 Async 后缀的方法将使用 `ForkJoinPool.commonPool` 实例以并发方式执行。

不带 Async 后缀的版本将以串行方式执行。

带 Async 后缀并且以一个执行器实例作为额外参数的版本中，CompletableFuture 将在作为参数传递的执行器中以异步方式执行。



Java 9 增加了一些方法，为 CompletableFuture 类赋予了更强的功能。

- `defaultExecutor()`

  方法用于返回并不接收 Executor 作为参数的那些异步操作的默认执行器。通常，它将是 `ForkJoinPool.commonPool()` 方法的返回值。

- `copy()`

  方法创建 CompletableFuture 对象的一个副本。如果原来的 CompletableFuture 正常完成，则副本方法也将正常完成并返回相同的值。

  如果原来的 CompletableFuture 异常完成，则副本方法也异常完成，并且抛出 CompletionException 异常。

- `completeAsync()`

  方法接收一个 Supplier 对象作为参数（还可以选择 Executor）。借助 Supplier 的结果完成 CompletableFuture。

- `orTimeout()`

  方法接收一段时延。如果 CompletableFuture 在这段时间之后没有完成，那么抛出 TimeoutException 异常并异常完成。

- `completeOnTimeout()`

  方法与上一个方法相似，只不过它在作为参数的值的范围内正常完成。

- `delayedExecutor()`

  方法返回一个 Executor，该执行器在执行指定时延之后执行某一任务。



# 并发数据结构

Java 并发 API 中提供了两种并发数据结构。

- **阻塞型数据结构**

  这种类型的数据结构提供了插入数据和删除数据的方法，当操作无法立即执行时（例如数据结构为空），执行调用的线程就会被阻塞，直到可以执行该操作为止。

- **非阻塞型数据结构**

  这种类型的数据结构提供了插入数据和删除数据的方法，当无法立即执行操作时，返回一个特定值或者抛出一个异常。

有时非阻塞型数据结构会有一个与之等效的阻塞型数据结构。

ConcurrentLinkedDeque 类是一个非阻塞型数据结构，而 LinkedBlockingDeque 类则是一个与之等效的阻塞型数据结构。

阻塞型数据结构的一些方法具有非阻塞型数据结构的行为。

Deque 接口定义了 `pollFirst()` 方法，如果双端队列为空，该方法并不会阻塞，而是返回 null 值。另一方面，`getFirst()` 方法在这种情况下会抛出异常。每个阻塞型队列的实现都实现了该方法。



Java 集合框架（Java collections framework，JCF）提供了一个包含多种可用于串行编程的数据结构集合。

Java 并发 API 对这些数据结构进行了扩展，提供了另外一些可用于并发应用程序的数据结构，包括如下两项。

- **接口**：扩展了 JCF 提供的接口，添加了一些可用于并发应用程序的方法。
- **类**：实现了前面的接口，提供了可以用于应用程序的具体实现。



## 接口

### Queue

队列是一种线性数据结构，允许在队列的末尾插入元素且从队列的起始位置获取元素。它是一个 FIFO 型数据结构，第一个进入队列的元素将是第一个被处理的元素。

Queue 接口定义了在队列中执行的基本操作。该接口提供了实现如下操作的方法。

- 在队列的末尾插入一个元素。
- 从队列的首部开始检索并删除一个元素。
- 从队列的首部开始检索一个元素但不删除。

对于这些方法，该接口定义了两个版本。它们在方法执行时具有不同的表现。

- 可以抛出异常的方法。
- 可以返回某一特定值的方法，例如 false 或 null 。

| 操作         | 抛出异常  | 返回特殊值 |
| ------------ | --------- | ---------- |
| 插入         | add()     | offer()    |
| 检索并删除   | remove()  | poll()     |
| 检索但不删除 | element() | peek()     |



### BlockingQueue

BlockingQueue 接口扩展了 Queue 接口，添加了当操作不可执行时阻塞调用线程的方法。

| 操作         | 阻塞   |
| ------------ | ------ |
| 插入         | put()  |
| 检索并删除   | take() |
| 检索但不删除 | N/A    |



### Deque

与队列一样，双端队列也是一种线性数据结构，但是允许从该数据结构的两端插入和删除元素。

Deque 接口扩展了 Queue 接口。除了 Queue 接口提供的方法之外，它还提供了从两端执行插入、检索且删除、检索但不删除等操作的方法。

| 操作         | 抛出异常                    | 返回特定值                |
| ------------ | --------------------------- | ------------------------- |
| 插入         | addFirst()、addLast()       | offerFirst()、offerLast() |
| 检索并删除   | removeFirst()、removeLast() | pollFirst()、pollLast()   |
| 检索但不删除 | getFirst()、getLast()       | peekFirst()、peekLast()   |



### BlockingDeque

BlockingDeque 接口扩展了 Deque 接口，添加了当操作无法执行时阻塞调用线程的方法。

| 操作         | 阻塞                    |
| ------------ | ----------------------- |
| 插入         | putFirst()、putLast()   |
| 检索并删除   | takeFirst()、takeLast() |
| 检索但不删除 | N/A                     |



### TransferQueue

该接口扩展了 BlockingQueue 接口，并且增加了将元素从生产者传输到消费者的方法。在这些方法中，生产者可以一直等到消费者取走其元素为止。该接口添加的新方法有如下几项。

- `transfer()`

  将一个元素传输给一个消费者，并且等待（阻塞调用线程）该元素被使用。

- `tryTransfer()`

  如果有消费者等待，则传输一个元素。否则，该方法返回 false 值，并且不将该元素插入队列。



### ConcurrentMap

Map 接口定义了使用 map 的基本操作。ConcurrentMap 扩展了 Map 接口，为并发应用程序提供了相同的方法。

接口在 Java 8 中做了修改，包含了下述方法。

- `forEach()`

  该方法针对 map 的所有元素执行给定函数。

- `compute()`、`computeIfAbsent()` 和 `computeIfPresent()`

  这些方法允许指定一个函 数，该函数用于计算与某个键相关的新值。

- `merge()`

  该方法允许指定将某个键值对合并到某个已有的 map 中。如果 map 中没有该键，则直接插入，否则，执行指定的函数。



## 类

### LinkedBlockingQueue

该类实现了 BlockingQueue 接口，提供了一个带有阻塞型方法的队列，该方法可以有任意有限数量的元素。该类还实现了 Queue、Collection 和 Iterable 接口。



### ConcurrentLinkedQueue

该类实现了 Queue 接口，提供了一个线程安全的无限队列。从内部来看，该类使用一种非阻塞型算法保证应用程序中不会出现数据竞争。



### LinkedBlockingDeque

该类实现了 BlockingDeque 接口，提供了一个带有阻塞型方法的双端队列，它可以有任意有限数量的元素。

LinkedBlockingDeque 具有比 LinkedBlockingQueue 更多的功能，但是其开销更大。因此，应在双端队列特性不必要的场合使用 LinkedBlockingQueue 类。



### ConcurrentLinkedDeque

该类实现了 Deque 接口，提供了一个线程安全的无限双端队列，它允许在双端队列的两端添加和删除元素。

它具有比 ConcurrentLinkedQueue 更多的功能，但与 LinkedBlockingDeque 相同，该类开销更大。



### ArrayBlockingQueue

该类实现了 BlockingQueue 接口，基于一个数组提供了阻塞型队列的一个实现，可以有有限个元素。它还实现了 Queue、Collection 和 Iterable 接口。

与基于数组的非并发数据结构（ArrayList、 ArrayDeque）不同，ArrayBlockingQueue 按照构造函数中所指定的固定大小为数组分配空间，而且不可再调整其大小。



### DelayQueue

该类实现了 BlockingDeque 接口，提供了一个带有阻塞型方法和无限数目元素的队列实现。

该队列的元素必须实现 Delayed 接口，因此它们必须实现 `getDelay()` 方法。如果该方法返回一个负值或 0，那么延时已过期，可以取出队列的元素。位于队列首部的是延时负数值最小的元素。



### LinkedTransferQueue

该类提供了一个 TransferQueue 接口的实现。它提供了一个元素数量无限的阻塞型队列。这些元素有可能被用作生产者和消费者之间的通信信道。在那里，生产者可以等待消费者处理它们的元素。



### PriorityBlockingQueue

该类提供了 BlockingQueue 接口的一个实现，在该类中可以按照元素的自然顺序选择元素，也可以通过该类构造函数中指定的比较器选择元素。该队列的首部由元素的排列顺序决定。



### ConcurrentHashMap

该类提供了 ConcurrentMap 接口的一个实现。它提供了一个线程安全的哈希表。除了 Java 8 中 Map 接口新增加的方法之外，该类还增加了其他一些方法。

- `search()`、`searchEntries()`、`searchKeys()` 和 `searchValues()`

  这些方法允许对键值对、键或者值应用搜索函数。这些搜索功能可以是一个 lambda 表达式。搜索函数返回一个非空值时，该方法结束。这也是该方法的执行结果。

- `reduce()`、`reduceEntries()`、`reduceKeys()` 和 `reduceValues()`

  这些方法允许应用一个 `reduce()` 操作转换键值对、键，或者将其整个哈希表作为流处理。

- `reduceToInt()`、`reduceToDouble()`、`reduceToLong()`

- `reduceEntriesToDouble()`、`reduceEntriesToInt()`、`reduceEntriesToLong()`

- `reduceKeysToDouble()`、`reduceKeysToInt()`、`reduceKeysToLong()`

- `reduceValuesToDouble()`、`reduceValuesToInt()`、`reduceValuesToLong()`



## 原子变量

原子变量是在 Java 1.5 中引入的，用于提供针对 integer、long、boolean、reference 和 Array 对象的原子操作。

它们提供了一些方法来递增值、递减值、确定值、返回值，或者在其当前值等于预定义值时确定值。

原子变量提供了与 volatile 关键字相似的保障。

Java 8 中增加了四个新类，DoubleAccumulator、DoubleAdder、LongAccumulator 和 LongAdder。

LongAdder 与 AtomicLong 类似，但是当经常更新来自不同线程的累加操作并且只需要在操作的末端给出结果时，具有更好的性能。

- `add()` 为计数器增加参数中指定的值。

- `increment()`

  相当于 `add(1)` 。

- `decrement()`

  相当于 `add(-1)` 。

- `sum()`

  该方法返回计数器的当前值。

DoubleAdder 类并没有 `increment()` 和 `decrement()` 方法。

LongAccumulator 类和 LongAdder 类很类似，但是也有一个非常明显的区别。它们都有一个可以指定如下两个参数的构造函数。

- 内部计数器的标识值。
- 一个将新值累加到累加器的函数。

该函数并不依赖于累加的顺序。在这种情况下，最重要的方法就是如下两种。

- `accumulate()`

  该方法接收一个long 值作为参数。它应用函数对计数器进行递增或递减操作，使之成为当前值和参数指定值。

- `get()`

  返回计数器的当前值。

  

## 变量句柄

**变量句柄（variable handle**是一种对变量、静态域或数组元素的动态型引用，使之可以用多种不同的模式访问该变量。

例如，可以在并发应用程序中对变量进行访问保护，实现对该变量的原子访问。

在此之前，只能通过原子变量获得这样的行为，但是现在可以使用变量句柄获得同样的功能，而不需要采用任何同步机制。

这是 Java 9 中引入的一种新特性，由 VarHandle 类提供。变量句柄有如下几种访问方法。

- **读取访问模式**：根据不同方法，该模式允许按照不同的内存排序规则读取变量的值。

  - `get()`

    变量视为非易失性变量读取。

  - `getVolatile()`

    将变量作为易失性变量来读取。

  - `getAcquire()`

    确保对该变量的其他访问在该语句之前不会因为优化方面的原因而重新排序。

  - `getOpaque()`

    与 `getAcquire()` 类似，但是它仅对当前线程有影响。

- **写入访问模式**：该模式允许按照不同的内存排序规则写入变量的值。

  - `set()`
  - `setVolatile()`
  - `setRelease()`
  - `setOpaque()`

- **原子更新访问模式**：这种模式获得与原子变量类似的功能和操作，例如比较变量的值。

  - `compareAndSet()`

    如果作为参数传递的预期值和变量的当前值相等，那么改变变量的值， 就像变量是被声明为易失性变量一样。

  - `weakCompareAndSet() `和 `weakCompareAndSetPlain()`

    如果作为参数传递的预期值与变量的当前值相等，那么自动将变量的当前值替换为新值。

    第一种法将变量视为一个易失性 变量，而第二种法将变量视为一个非易失性变量。

- **数值型原子更新访问模式**：这种模式以原子方式修改数值。

  - `getAndAdd()`

    增加变量的值并且返回之前的值，因为该变量被原子自动声明为一个易失性变量。

- **位原子更新访问模式**：这种模式以原子方式按位修改值。

  - `getAndBitwiseOr()`
  - `getAndBitwiseAnd()`



# 反应流

反应流定义了描述必要操作和实体所需的接口、方法和协议的最小集合。

- 信息的发布者。
- 一个或多个信息订阅者。
- 发布者和消费者之间的订阅关系。

反应流规范根据以下规则明确了这些类应该如何交互。

- 发布者将添加那些希望得到通知的订阅者。
- 订阅者被发布者添加时会收到通知。
- 订阅者以异步方式请求来自发布者的一个或多个元素，也就是说，订阅者请求元素并继续其执行。
- 发布者有一个要发布的元素时，会将其发送给请求元素的所有订阅者。

所有这些通信都是异步的，因此可以充分利用多核处理器的全部性能。

Java 9 包含了三个接口，即 Flow.Publisher、Flow.Subscriber 和 Flow.Subscription， 以及一个实用工具类，SubmissionPublisher 类。它们可支持实现反应流应用程序。



## Java 反应流简介

- **Flow.Publisher 接口**

  描述了条目的生产者。

- **Flow.Subscriber 接口**

  描述了条目的使用者，即消费者。

- **Flow.Subscription 接口**

  描述了生产者与消费者之间的连接。实现该接口的类可以管理生产者和消费者之间的条目交换。

  

### Flow.Publisher 接口

- `subscribe()`

  该方法接收 Flow.Subscriber 接口的一个实现作为参数，并且将该订阅者添加到其内部订阅者列表。

  该方法并不返回任何结果。从内部来看，它使用 Flow.Subscriber 接口提供的方法向订阅者发送条目、错误信息和订阅对象。

  

### Flow.Subscriber 接口

- `onSubscribe()`

  该方法由发布者调用，用于完成订阅者的订阅过程。

  它向订阅者发送了 Flow.Subscription 对象，该对象管理发布者和订阅者之间的通信。

- `onNext()`

  当发布者想把新条目发送给订阅者时，会调用该方法。

  在该方法中，订阅者必须处理该条目。该方法并不返回任何结果。

- `onError()`

  如果出现了一个不可恢复的错误，而且没有调用其他的订阅者方法，那么发布者将调用该方法。

  该方法接收 Throwable 对象作为参数，其中含有已发生的错误。

- `onComplete()`

  不再发送任何条目时，发布者将调用该方法。该方法没有参数，也不返回结果。

  

### Flow.Subscription 接口

- `cancel()`

  订阅者调用该方法告诉发布者它不再需要任何条目了。

- `request()`

  订阅者调用该方法来告诉发布者它需要更多的条目。它将订阅者想要的条目数作为参数。

  

### SubmissionPublisher 类

SubmissionPublisher 实现了 Flow.Publisher 接口。它还使用 Flow.Subscription 接口，并且提供向消费者发送条目的方法，这些方法用于了解消费者数量、发布者和消费者之间的订阅关系，以及关闭它们之间的通信。下面给出了该类比较重要的方法。

- `subscribe()`

  该方法由 Flow.Publisher 接口提供，用于向发布者订阅一个 Flow.Subscriber 对象。

- `offer()`

  该方法以异步方式调用其 `onNext()` 方法，向每个订阅者发布一个条目。

- `submit()`

  该方法以异步方式调用其 `onNext()`方法，向每个订阅者发布一个条目。资源对任何订阅者都不可用时，进行不间断阻塞。

- `estimateMaximumLag()`

  该方法对发布者已生成但尚未被已订阅的订阅者使用的条目进行估计。

- `estimateMinimumDemand()`

  该方法对消费者已请求但是发布者尚未生成的条目数进行估计。

- `getMaxBufferCapacity()`

  该方法返回每个订阅者的最大缓冲区。

- `getNumberOfSubscribers()`

  该方法返回订阅者的数量。

- `hasSubscribers()` 该方法返回一个布尔值，该值用于指示发布者是否有订阅者。

- `close()`

  该方法调用当前发布者的所有订阅者的 `onComplete()` 方法。

- `isClosed()`

  该方法返回一个布尔值，用于指示当前发布者是否已关闭。



## 设计小结

使用 SubmissionPublisher 时：

Subscriber：实现相关方法（完成订阅、消费、异常处理）的逻辑。持有 Subscription，订阅者与生产者之前通过 Subscription 交互（要求更多的项目或者不再需要）。

向 SubmissionPublisher 中注册订阅者，Task 持有 SubmissionPublisher，通过 `submit()` 方法发送项目。可以启动多个 Task 来进行并发。



如果 SubmissionPublisher 提供的功能不符合需求，那么必须实现自己的发布者和订阅关系。

Subscriber：持有 subscription、categories。

Subscription：持有 categories，使用 AtomicLong 记录请求条目数量。

ConsumerData：封装一组 Subscriber 和 Subscription。

Publisher：持有 consumers 和 executor。

Task：实现 Runnable。完成判断、发布者把新条目发送给订阅者（ `Subscriber.onNext()` ）、请求数目减少的逻辑。


