# 测试与监视并发应用程序

## 监视并发对象

Java 并发 API 提供的大多数并发对象都含有可获知该对象状态的方法。这些状态包括当前正在执行的线程数、被阻断且等待某一条件的线程数、执行的任务数等。



### 监视线程

Thread 类提供了一些可以获取线程信息的方法。其中最有用的一些方法如下。

- `getId()`

  该方法返回线程的标识符。标识符是一个 long 型的正数，而且是唯一的。

- `getName()`

  该方法返回线程的名称。默认情况下，其命名格式为 Thread-xxx，不过线程名称可以在构造方法中修改，也可以使用 `setName()` 方法修改。

- `getPriority()`

  该方法返回线程的优先级。默认情况下，所有线程的优先级都为 5，但可以使用 `setPriority()` 方法来更改。优先较高的线程比优先级较低的线程更容易被优先选用。

- `getState()`

  该方法返回线程的状态。它返回 `Enum Thread.State` 中的一个值，且其取值可以为 NEW、RUNNABLE、BLOCKED、WAITING、TIMED_WAITING 和 TERMINATED。可查看 API 文档来了解每个状态的真实含义。

- `getStackTrace()`

  该方法将线程的调用栈作为一个 StackTraceElement 对象数组返回。可以打印该数组，以了解该线程被做了哪些调用。



### 监视锁

ReentrantLock 类有一些方法可以用来获知 Lock 对象的状态。

- `getOwner()`

  该方法返回一个 Thread 对象，其中含有当前加锁的线程，也就是说，该线程正在执行临界段。

- `hasQueuedThreads()`

  该方法返回一个布尔值，它表示是否有线程等待获取锁。

- `getQueueLength()`

  该方法返回一个 int 值，它表示当前等待获取锁的线程数。

- `getQueuedThreads()`

  该方法返回一个 `Collection<Thread>` 对象，其中含有当前等待获取锁的 Thread 对象。

- `isFair()`

  该方法返回一个布尔值，表示公平属性的状态。该属性的值用于判定下一个获取锁的线程。

- `isLocked()`

  该方法返回一个布尔值，表示锁是否归某个线程所有。

- `getHoldCount()`

  该方法返回一个 int 值，该值表示当前线程获取到锁的次数。如果当前线程并没有得到锁，则返回值为 0。否则，对于当前没有调用相匹配的 `unlock()` 方法的线程，该方法将返回 `lock()` 方法在该线程中被调用的次数。



### 监视执行器

ThreadPoolExecutor 提供了一些方用来获知执行器的状态。

- `getActiveCount()`

  该方法返回执行器中正在执行任务的线程数。

- `getCompletedTaskCount()`

  该方法返回执行器已经执行且已完成执行的任务数。

- `getCorePoolSize()`

  该方法返回核心线程数目。这一数目决定了线程池中的最小线程数。即使执行器中没有任务运行，线程池中的线程数也不会少于该方法所返回的数目。

- `getLargestPoolSize()`

  该方法返回执行器线程池已经同时执行过的最大线程数。

- `getMaximumPoolSize()`

  该方法返回执行器线程池中同时可以存在的最大线程数。

- `getPoolSize()`

  该方法返回线程池中当前的线程数。

- `getTaskCount()`

  该方法返回已经发送给执行器的任务数，包括正在等待、运行中和已经完成的任务。

- `isTerminated()`

  如果调用了 `shutdown()` 或 `shutdownNow()` 方法并且执行器已完成了所有未完成任务的执行，则该方法返回 true，否则返回 false。

- `isTerminating()`

  如果调用了 `shutdown()` 或 `shutdownNow()` 方法，但是执行器仍然在执行任务，则该方法返回 true。



### 监视 Fork/Join 框架

ForkJoinPool 类提供了如下方法以获取其状态。

- `getParallelism()`

  该方法返回线程池确立的并行处理的预期层级。

- `getPoolSize()`

  该方法返回线程池中的线程数。

- `getActiveThreadCount()`

  该方法返回线程池中当前执行任务的线程数。

- `getRunningThreadCount()`

  该方法返回并不等待其子任务完成的线程的数量。

- `getQueuedSubmissionCount()`

  该方法返回已经提交给线程池但是尚未开始执行的任务数。

- `getQueuedTaskCount()`

  该方法返回线程池工作窃取队列中的任务数。

- `hasQueuedSubmissions()`

  如果有任务提交给线程池且尚未开始执行，则该方法返回 true，否则返回 false。

- `getStealCount()`

  该方法返回 Fork/Join 池执行工作窃取算法的次数。

- `isTerminated()`

  如果 Fork/Join 池完成执行，则该方法返回 true，否则返回 false。



### 监视 Phaser

Phaser 提供了一些用于获取 Phaser 状 态的方法。

- `getArrivedParties()`

  该方法返回已经完成当前阶段的已注册参与方的数量。

- `getUnarrivedParties()`

  该方法返回尚未完成当前阶段的已注册参与方的数量。

- `getPhase()`

  该方法返回当前阶段的编号。第一个阶段的编号为 0。

- `getRegisteredParties()`

  该方法返回 Phaser 中已注册参与方的数量。

- `isTerminated()`

  该方法返回一个布尔值，用于指示 Phaser 是否已经完成执行。



### 监视 Stream API

使用 `peek()` 方法。



## 监视并发应用程序

**JConsole** `JDK 安装目录\bin\jconsole.exe`

通过在 Local Process 区域选定某一进程，可以监视那些在计算机上运行的进程，也可以在 Remote Process 区域引入远程进程的数据以监视远程进程。

到屏幕上有 6 个选项卡。

- **Overview**

  有关该应用程序的一般信息。

- **Memory**

  有关内存使用情况的信息。

- **Threads**

  应用程序的线程随时间推移的演变情况，而且允许查看某一线程的详细信息。

- **Classes**

  当前加载类的信息以及类的数量。

- **VM Summary**

  运行进程的 Java 虚拟机的信息。

- **MBean**

  进程的 MBean。Mbean 是一个托管的 Java 对象，可以表示设备、应用程序或者任何资源，而且它是 JMX API 的基础。



 ### Overview 选项卡

- **Heap Memory Use**

  展示了应用程序使用的内存大小。它也展现了已用内存、指定内存和最大内存。

- **Threads**

  展示了应用程序所使用线程数的演变情况。其中含有程序员以显式方式创建的线程和由 JVM 所创建的线程。

- **Classes**

  展示了应用程序加载的类的数量。

- **CPU Usage**

  展示了应用程序 CPU 使用的变化情况。



### Memory 选项卡

在屏幕的上方，有一个供你选择内存类型的下拉菜单。

该菜单提供了多种不同的选项，例如堆内存、非堆内存，以及特定的内存工具，例如 Eden Space 用于展示最初为大多数对象分配的内存的信息，而 Survivor Space 则用于展示维持 Eden Space 垃圾收集器的对象所使用的内存。

之后，可以得到选定元素随时间演变的图示。最后，还有一个 Details 区域，用于展示内存消耗信息。

- **Used**

  展示应用程序当前的内存使用量。

- **Committed**

  用于保障 JVM 执行的内存量。

- **Max**

  JVM 可以使用的最大内存量。

- **GC time**

  花费在垃圾收集上的时间。



### Threads 选项卡

展示了线程数随时间变化的演变情况。Live Threads 是当前正在运行的线程数，而 Peak 线程数则是最大线程数。

在底部，窗口的左部是所有当前所有线程的列表。如果选定其中一个线程，那么在右侧就会看到关于该线程的信息，例如该线程的名称、状态和当前栈追踪情况。



### Classes 选项卡

红线表示应用程序加载的类的总数，而蓝线则表示当前加载的类的数量。

选项卡的底部是细节展示区，其中含有当前信息。

- 当前加载的类。
- 总共加载的类。
- 尚未加载的类。



### VM Summary 选项卡

- 摘要区域：这一块区域展示了有关正在运行进程的 Java 虚拟机实现的信息。
  - Virtual Machine：正在执行进程的 Java 虚拟机的名称。
  - Vendor：实现该 Java 虚拟机的组织名称。
  - Name：运行进程的机器名称。
  - Uptime：从 JVM 启动到现在经过的时间。
  - Process CPU time：JVM 消耗的 CPU 时间。
- 线程区域：该区域展示了有关应用程序线程的信息。
  - Live threads：当前运行的线程总数。
  - Peak：在 JVM 中执行的最高线程数。
  - Daemon threads：当前运行的守护线程总数。
  - Total threads started：自 JVM 开始运行后开始执行的线程总数。
- 类区域：该区域展示了有关应用程序类的数量的信息。
  - Current classes loaded：当前加载到内存中的类的数量。
  - Total classes loaded：JVM 开始运行后加载到内存中的类的数量。
  - Total classes unloaded：JVM 开始运行后从内存中卸载的类的数量。
- 内存区：该区域展示了应用程序的内存使用情况。
  - Current heap size：当前堆的规模。
  - Committed memory：为堆的使用分配的内存总量。
  - Maximum heap size：堆的最大规模。
  - Garbage collector：垃圾收集器的相关信息。
- 操作系统区：该区域展示了有关执行 Java 虚拟机的操作系统的信息。
  - Operating System：运行 JVM 的操作系统的版本。
  - Number of Processors：计算机所配置的核的数量或 CPU 数量。
  - Total physical memory：操作系统可用的 RAM 总量。
  - Free physical memory：操作系统可用的空闲 RAM 总量。
  - Committed virtual memory：保证当前进程运行的内存。
- 其他信息：该区域展示了关于 Java 虚拟机的其他信息。
  - VM arguments：传递给 JVM 的参数。
  - Class path：JVM 的类路径。
  - Library path：JVM 的库路径。
  - Boot class path：JVM 寻找 java.*和 javax.*类的路径。



### MBeans 选项卡

在该选项卡的左侧，可以在目录树中看到所有正在运行的 MBean。选定其中一项，将在选项卡的 右侧看到MBean Info 和 MBean Descriptor 的内容。

并发应用程序可用 Threading MBean 表示，它共有两个区域。Attributes 区域包含 MBean 的属性，而 Operations 区域包含所有可以通过该 MBean 运行的操作。

