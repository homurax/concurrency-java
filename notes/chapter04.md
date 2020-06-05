# 充分利用执行器

## 执行器的高级特性

### 任务的撤销

将任务发送给执行器之后，还可以撤销该任务的执行。

使用 `submit()` 方法将 Runnable 对象发送给执行器时，它会返回 Future 接口的一个实现。

该类允许控制该任务的执行。该类有 `cancel()` 方法，可用于撤销任务的执行。该方法接收一个布尔值作为参数，如果接收到的参数为 true，那么执行器执行该任务，否则执行该任务的线程会被中断。



想要撤销的任务无法被撤销的情形。

- 任务已经被撤销。
- 任务已经完成了执行。
- 任务正在执行而提供给 `cancel()` 方法的参数为 false。
- 在 API 文档中并未说明的其他原因。

`cancel()` 方法返回了一个布尔值，用于表明当前任务是否被撤销。



### 任务执行调度

Java 并发 API 为 ThreadPoolExecutor 提供了一个扩展类 ScheduledThreadPoolExeuctor ，以支持预定任务的执行。

- 在某段延迟之后执行某项任务。
- 周期性地执行某项任务，包括以固定速率执行任务或者以固定延迟执行任务。



### 重载执行器方法

可以通过扩展一个已有的类（ThreadPoolExecutor 或 ScheduledThreadPoolExecutor）实现自己的执行器，获得想要的行为。

这些类中包括一些便于改变执行器工作方式的方法。如果重载了 ThreadPoolExecutor 类，就可以重载以下方法。

- `beforeExecute()`

  该方法在执行器中的某一并发任务执行之前被调用。

  它接收将要执行的 Runnable 对象和将要执行这些对象的 Thread 对象。该方法接收的 Runnable 对象是 FutureTask 类的一个实例，而不是使用 `submit()` 方法发送给执行器的 Runnable 对象。

- `afterExecute()`

  该方法在执行器中的某一并发任务执行之后被调用。

  它接收的是已执行的 Runnable 对象和一个 Throwable 对象，该 Throwable 对象存储了任务中可能抛出的异 常。Runnable 对象是 FutureTask 类的一个实例。

- `newTaskFor()`

  该方法创建的任务将执行使用 `submit()` 方法发送的 Runnable 对象。

  该方法必须返回 RunnableFuture 接口的一个实现。默认情况下，Open JDK 9 和 Oracle JDK 9 返回 FutureTask 类的一个实例，但是这在今后的实现中可能会发生变化。



如果扩展 ScheduledThreadPoolExecutor 类，可以重载 `decorateTask()` 方法。该方法与面向预定任务的 `newTaskFor()` 方法类似,并且允许重载执行器所执行的任务。



### 更改一些初始化参数

可以在执行器创建之时更改一些参数以改变其行为。最常用的一些参数如下所示。

- BlockingQueue<Runnable>

  每个执行器均使用一个内部的 BlockingQueue 存储等待执行的任务。可以将该接口的任何实现作为参数传递。例如，更改执行器执行任务的默认顺序。

- ThreadFactory

  可以指定 ThreadFactory 接口的一个实现，而且执行器将使用该工厂创建执行该任务的线程。例如，可以使用 ThreadFactory 接口创建 Thread 类的一个扩展类，保存有关任务执行时间的日志信息。

- RejectedExecutionHandler

  调用 `shutdown()` 方法或者 `shutdownNow()` 方法之后，所有发送给执行器的任务都将被拒绝。可以指定 RejectedExecutionHandler 接口的一个实现管理这种情形。



## 高级服务器应用程序

### executor

#### ExecutorStatistics

统计对象，用于计算每个用户在服务器上执行的任务数量，以及这些任务的总执行时间。

属性都是支持在单个变量上进行原子操作的 AtomicVariables 型变量，可以在不同的线程中使用这些变量，且不需要采用任何同步机制。

该类还有两个方法可以分别增加任务数和执行时间。

#### RejectedTaskController

被拒绝任务控制器，实现 RejectedExecutionHandler 接口，用以管理其拒绝的任务。

如果在执行器已调用 `shutdown()` 或 `shutdownNow()` 方法之后提交任务，则该任务会被执行器拒绝。

每个被拒绝的任务都要调用一次 `rejectedExecution()` 方法，而该方法将接收被拒绝的任务和拒绝该任务的执行器作为参数。

#### ServerTask

执行器任务，扩展 FutureTask。

#### ServerExecutor

自定义执行器，扩展 ThreadPoolExecutor。

构造方法调用父类的构造方法时，BlockingQueue 使用 PriorityBlockingQueue，以此根据 `compareTo()` 方法的执行结果对元素进行排序，按照优先级执行任务。

### server

#### RequestTask

SimpleServer 中为每个查询创建一个 RequestTask 对象，并且将其发送给执行器。

此处只有一个将作为线程执行的 RequestTask 的实例，在其中从并发容器中获取 Socket，创建命令并且将其发送给执行器。

这样更改的主要原因是为了只在任务中留下执行器要执行的查询代码，而将预处理代码放在执行器之外。

#### ConcurrentServer

包含服务器的 `main()` 方法以及额外用于撤销任务和结束系统执行的方法。



### ★ 小结

自定义执行器可以通过指定 BlockingQueue、RejectedExecutionHandler 的具体实现，来达到所需的目的。通过重写 `beforeExecute()` 、`afterExecute()` 方法，可以对一个任务开始前后实现一些功能，比如记录信息、执行时间。重写 `newTaskFor()` 方法用来实现使用自己的 `FutureTask` 实现。



主线程中将不断接收到的资源放入并发容器，并发容器作为属性提供给 Task 类。

Task 类中实例化自定义执行器，`run()` 方法中循环从并发容器中消费资源，发送给执行器。



## 执行周期性任务

实现一个 RSS 订阅程序，定期执行同一任务（阅读 RSS 订阅上的新闻）。

- 在文件中存储 RSS 源。
- 对每个 RSS 源，向执行器发送一个 Runnable 对象。每当执行器运行对象时，解析 RSS 源并且将其转换成一个含有 RSS 内容的 CommonInformationItem 对象列表。
- 使用生产者/消费者模式将 RSS 新闻写入磁盘。生产者是执行器的任务，它们将每个 CommonInformationItem 写入到缓存中，缓存中仅存储新条目。消费者是一个独立线程， 它从缓存中读取新闻并将其写入磁盘。



因为将在 ScheduledThreadPoolExeuctor 中执行这些任务，必须实现一个类用以扩展 FutureTask 类，以及实现 RunnableScheduledFuture 接口。该接口提供了 `getDelay()` 方法，该方法返回了距离任务下一 次执行所剩余的时间。

FutureTask 类的 `runAndReset()` 方法执行任务并且重置其状态，这样任务就可以再次执行。

在 ScheduledThreadPoolExecutor 类的队列中再次插入该任务：`executor.getQueue().add(this)`  。



## 有关执行器的其他信息

- `shutdown()`

  必须显式调用该方法以结束执行器的执行，也可以重载该方法，加入一些代码释放执行器所使用的额外资源。

- `shutdownNow()`

  `shutdown()` 和 `shutdownNow()` 之间的区别在于 `shutdown()` 要等待执行器中所有处于等待状态的任务全部终结。

- `submit()`、`invokeAll()`、`invokeAny()`

  可以调用这些方法向执行器发送并发任务。如果需要在将任务插入到执行器任务队列之前或之后进行一些操作，就可以重载这些方法。

  在任务进行排队之前或之后添加定制操作与在该任务执行之前或之后添加定制操作是不同的，这些操作要考虑到重载 `beforeExecute()` 方法和 `afterExecute()` 方法。



ScheduledThreadPoolExecutor 类还有其他一些方法可用于执行周期性任务或者延迟之后的任务。

- `schedule()`

  该方法在给定延迟之后执行某个任务，且该任务仅执行一次。

- `scheduleAtFixedRate()`

  该方法按照给定周期执行一个周期性任务。

  它与 `scheduleWithFixedDelay()` 方法的区别在于，对于后者而言，两次执行之间的延迟是指第一次执行 结束之后到第二次执行之前的时间；而对于前者而言，两次执行之间的延迟是指两次执行起始之间的时间。



