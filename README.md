# Chapter01 第一步：并发设计原理

## 1.1 基本的概念

关于并发，最被人们认可的定义是，在单个处理器上采用单核执行多个任务即为并发。  
对于并行来说也有同样的定义：同一时间在不同的计算机、处理器或处理器核心上同时运行多个任务，就是所谓的“并行”。  

另一个关于并发的定义是，在系统上同时运行多个任务（不同的任务）就是并发。  
而另一个关于并行的定义是：同时在某个数据集的不同部分之上运行同一任务的不同实例就是并行。  

**关于并行的最后一个定义是，系统中同时运行了多个任务。关于并发的最后一个定义是，一种解释程序员将任务和它们对共享资源的访问同步的不同技术和机制的方法。**  

**控制同步**：当一个任务的开始依赖于另一个任务的结束时，第二个任务不能在第一个任务完成之前开始。  
**数据访问同步**：当两个或更多任务访问共享变量时，在任意时间里，只有一个任务可以访问该变量。  
**临界段**是一段代码，由于它可以访问共享资源，因此在任何给定时间内，只能够被一个任务执行。**互斥**是用来保证这一要求的机制，而且可以采用不同的方式来实现。  

**原子操作**是一种发生在瞬间的操作。在并发应用程序中，可以通过一个临界段来实现原子操作，以便对整个操作采用同步机制。  
**原子变量**是一种通过原子操作来设置和获取其值的变量。可以使用某种同步机制来实现一个原子变量，或者也可以使用CAS 以无锁方式来实现一个原子变量，而这种方式并不需要任何同步机制。  


## 1.2 可能出现的问题

- 数据竞争
- 死锁 
- 活锁：系统中有两个任务，它们总是因对方的行为而改变自己的状态。  
- 资源不足 
- 优先权反转：一个低优先权的任务持有了一个高优先级任务所需的资源。  


## 1.3 Java并发API

### 执行器

- [Interface Executor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executor.html)
- [Interface ExecutorService](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
- [Class ThreadPoolExecutor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ThreadPoolExecutor.html)
- [Class ScheduledThreadPoolExecutor](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledThreadPoolExecutor.html)
- [Class Executors](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executors.html)

### 同步机制

- [Class ReentrantLock](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/ReentrantLock.html)
- [Class ReentrantReadWriteLock](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html)
- [Class StampedLock](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/locks/StampedLock.html)
- [Class CountDownLatch](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CountDownLatch.html)
- [Class CyclicBarrier](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CyclicBarrier.html)

### Fork/Join 框架

- ForkJoinPool：该类实现了要用于运行任务的执行器。
- ForkJoinTask：这是一个可以在ForkJoinPool 类中执行的任务。
- ForkJoinWorkerThread：这是一个准备在ForkJoinPool 类中执行任务的线程。

### 并发数据结构 

阻塞型数据结构：这些数据结构含有一些能够阻塞调用任务的方法，例如，当数据结构为空而又要从中获取值时。  
非阻塞型数据结构：如果操作可以立即进行，它并不会阻塞调用任务。否则，它将返回null值或者抛出异常。  

- ConcurrentLinkedDeque：非阻塞型的列表。
- ConcurrentLinkedQueue：非阻塞型的队列。
- LinkedBlockingDeque：阻塞型的列表。
- LinkedBlockingQueue：阻塞型的队列。
- PriorityBlockingQueue：基于优先级对元素进行排序的阻塞型队列。
- ConcurrentSkipListMap：非阻塞型的NavigableMap。
- ConcurrentHashMap：非阻塞型的哈希表。
- AtomicBoolean、AtomicInteger、AtomicLong 和AtomicReference：基本Java数据类型的原子实现。

### 并发设计模式

- 信号模式：ReentrantLock、Semaphore 或是 `Object` 类中的 `wait()` 方法和 `notify()` 方法。  
- 会合模式：信号模式的推广。第一个任务将等待第二个任务的某一事件，而第二个任务又在等待第一个任务的某一事件。  
- 互斥模式：临界段(ReentrantLock、Semaphore)，保护一段代码或者一个完整的方法(synchronized)。  
- 多元复用模式：互斥机制的推广。Semaphore 。  
- 栅栏模式：CyclicBarrier 。  
- 双重检查锁定模式  
- 读-写锁模式：ReentrantReadWriteLock、StampedLock 。  
- 线程池模式  
- 线程局部存储模式：ThreadLocal 。  


## 1.4 设计并发算法的提示和技巧

- 正确识别独立任务：使用并发还是串行。  
- 在尽可能高的层面上实施并发处理  
- 考虑伸缩性  
- 使用线程安全API  
- 绝不要假定执行顺序：如果不采用任何同步机制，那么在并发应用程序中任务的执行顺序是不确定的。任务执行的顺序以及每个任务执行的时间，是由操作系统的调度器所决定的。  
- 在静态和共享场合尽可能使用局部线程变量  
- 寻找更易于并行处理的算法版本  
- 尽可能使用不可变对象  
- 通过对锁排序来避免死锁：在并发应用程序中避免死锁的最佳机制之一是强制要求任务总是以相同顺序获取资源。  
- 使用原子变量代替同步  
- 占有锁的时间尽可能短  
- 谨慎使用延迟初始化  
- 避免在临界段中使用阻塞操作  



# Chapter02 使用基本元素：Thread 和 Runnable


## 2.1 Java 中的线程

创建执行线程有两种方法。
- 扩展 Thread 类，并重载`run()`方法。
- 实现 Runnable 接口，并将该类的对象传递给 Thread 对象的构造函数。

这两种情况下都会得到一个 Thread 对象，但是相对于第一种方式来说，更推荐使用第二种。
- Runnable 是一个接口：可以实现其他接口并扩展其他类。对于采用 Thread 类的方式，只能扩展这一个类。
- 可以通过线程来执行 Runnable 对象，但也可以通过其他类似执行器的Java并发对象来执行。
- 可以通过不同线程使用同一 Runnable 对象。一旦有了 Thread 对象，就必须使用`start()`方法创建新的执行线程并且执行 Thread 类的`run()`方法。如果直接调用`run()`方法，那么将调用常规Java方法而不会创建新的执行线程。

Java中的所有线程都有一个优先级，这个整数值介于`Thread.MIN_PRIORITY`和`Thread.MAX_PRIORITY`之间，所有线程在创建时其默认优先级都是`Thread.NORM_PRIORITY`。

线程所有可能的状态都在`Thread.States`类中定义。
- NEW：Thread 对象已经创建，但是还没有开始执行。
- RUNNABLE：Thread 对象正在Java 虚拟机中运行。
- BLOCKED：Thread 对象正在等待锁定。
- WAITING：Thread 对象正在等待另一个线程的动作。
- TIME_WAITING：Thread 对象正在等待另一个线程的操作，但是有时间限制。
- THREAD：Thread 对象已经完成了执行。

Thread类的其他常用方法。
- `getId()`：该方法返回 Thread 对象的标识符。该标识符是在线程创建时分配的一个正整数。在线程的整个生命周期中是唯一且无法改变的。
- `getName()`/`setName()`：这两种方法允许获取或设置 Thread 对象的名称。这个名称是一个 String 对象，也可以在Thread 类的构造函数中建立。
- `getPriority()`/`setPriority()`：可以使用这两种方法来获取或设置 Thread 对象的优先级
- `isDaemon()`/`setDaemon()`：这两种方法允许获取或建立 Thread 对象的守护条件。
- `getState()`：该方法返回Thread 对象的状态。
- `interrupt()`/`interrupted()`/`isInterrupted()`：第一种方法表明正在请求结束执行某个 Thread 对象。另外两种方法可用于检查中断状态。这些方法的主要区别在于，调用`interrupted()`方法时将清除中断标志的值， 而`isInterrupted()`方法不会。调用`interrupt()`方法不会结束 Thread 对象的执行。Thread 对象负责检查标志的状态并做出相应的响应。
- `sleep()`：该方法允许将线程的执行暂停一段时间。它将接收一个 long 型值作为参数，该值代表想要 Thread 对象暂停执行的毫秒数。
- `join()`：这个方法将暂停调用线程的执行，直到调用该方法的线程执行结束为止。可以使用该方法等待另一个 Thread 对象结束。
- `setUncaughtExceptionHandler()`：当线程执行出现未校验异常时，该方法用于建立未校验异常的控制器。
- `currentThread()`：这是Thread 类的静态方法，它返回实际执行该代码的 Thread 对象。



# Chapter03 管理大量线程：执行器


## 3.1 执行器的基本特征

- 不需要创建任何 Thread 对象。如果要执行一个并发任务，只需要创建一个执行该任务的实例并且将其发送给执行器。执行器会管理执行该任务的线程。
- 执行器通过重新使用线程来缩减线程创建带来的开销。在内部，执行器管理着一个线程池，其中的线程称为工作线程（worker-thread）。如果向执行器发送任务而且存在某一空闲的工作线程，那么执行器就会使用该线程执行任务。
- 使用执行器控制资源很容易。可以限制执行器工作线程的最大数目。如果发送的任务数多于工作线程数，那么执行器就会将任务存入一个队列。当工作线程完成某个任务的执行后，将从队列中调取另一个任务继续执行。
- 必须以显式方式结束执行器的执行，必须告诉执行器完成执行之后终止所创建的线程。如若不然，执行器则不会结束执行，这样应用程序也不会结束。


## 3.2 执行器框架的基本组件

- Executor 接口：仅定义了一个方法，即允许编程人员向执行器发送一个 Runnable 对象。
- ExecutorService 接口：扩展了 Executor 接口并且包括更多方法，增加了该框架的功能。
    - 执行可返回结果的任务：Runnable 接口提供的`run()`方法并不会返回结果，但是借用执行器，任务可以返回结果。
    - 通过单个方法调用执行一个任务列表。
    - 结束执行器的执行并且等待其终止。
- ThreadPoolExecutor 类：该类实现了 Executor 接口和 ExecutorService 接口。此外，它还包含一些其他获取执行器状态（工作线程的数量、已执行任务的数量等）的方法、确定执行器参数（工作线程的最小和最大数目、空闲线程等待新任务的时间等）的方法，以及支持编程人员扩展和调整其功能的方法。
- Executors 类：该类为创建 Executor 对象和其他相关类提供了实用方法。


## 3.3 其他重要方法

通常阻塞型数据结构也会实现具有非阻塞型行为的方法，而非阻塞型数据结构并不会实现阻塞型方法。

实现**阻塞型**操作的方法如下。
- `put()`、`putFirst()`、`putLast()`：这些方法将一个元素插入数据结构。如果该数据结构已满，则会阻塞该线程，直到出现空间为止。
- `take()`、`takeFirst()`、`takeLast()`：这些方法返回并且删除数据结构中的一个元素。如果该数据结构为空，则会阻塞该线程直到其中有元素为止。

实现**非阻塞型**操作的方法如下。
- `add()`、`addFirst()`、`addLast()`：这些方法将一个元素插入数据结构。如果该数据结构已满，则会抛出一个 IllegalStateException 异常。
- `remove()`、`removeFirst()`、`removeLast()`：这些方法将返回并且删除数据结构中的一个元素。如果该结构为空，则这些方法将抛出一个 IllegalStateException 异常。
- `element()`、`getFirst()`、`getLast()`：这些方法将返回但是不删除数据结构中的一个元素。如果该数据结构为空，则会抛出一个 IllegalStateException 异常。
- `offer()`、`offerFirst()`、`offerLast()`：这些方法可以将一个元素插入数据结构。如果该结构已满，则返回一个 Boolean 值 false。
- `poll()`、`pollFirst()`、`pollLast()`：这些方法将返回并且删除数据结构中的一个元素。如果该结构为空，则返回 null 值。
- `peek()`、`peekFirst()`、`peekLast()`：这些方法返回但是并不删除数据结构中的一个元素。如果该数据结构为空，则返回 null 值。




# Chapter04 充分利用执行器


## 4.1 执行器的高级特性

### 任务的撤销

使用`submit()`方法将 Runnable 对象发送给执行器时，它会返回 Future 接口的一个实现。
该类允许控制该任务的执行。该类有`cancel()`方法，可用于撤销任务的执行。
该方法接收一个布尔值作为参数，如果接收到的参数为 true ，那么执行器执行该任务，否则执行该任务的线程会被中断。

以下是想要撤销的任务无法被撤销的情形。
- 任务已经被撤销。
- 任务已经完成了执行。
- 任务正在执行而提供给`cancel()`方法的参数为 false。
- 在API文档中并未说明的其他原因

`cancel()`方法返回了一个布尔值，用于表明当前任务是否被撤销。


### 任务执行调度

Java并发API为 ThreadPoolExecutor 类提供了一个扩展类，以支持预定任务的执行，这就是 ScheduledThreadPoolExecutor 类。
- 在某段延迟之后执行某项任务。
- 周期性地执行某项任务，包括以固定速率执行任务或者以固定延迟执行任务。


### 重载执行器方法

可以通过扩展一个已有的类（ThreadPoolExecutor 或者 ScheduledThreadPoolExecutor）实现自己的执行器，获得想要的行为。
这些类中包括一些便于改变执行器工作方式的方法。如果重载了 ThreadPoolExecutor 类，就可以重载以下方法。
- `beforeExecute()`：该方法在执行器中的某一并发任务执行之前被调用。它接收将要执行的 Runnable 对象和将要执行这些对象的 Thread 对象。该方法接收的 Runnable 对象是 FutureTask 类的一个实例，而不是使用`submit()`方法发送给执行器的 Runnable 对象。
- `afterExecute()`：该方法在执行器中的某一并发任务执行之后被调用。它接收的是已执行的 Runnable 对象和一个 Throwable 对象，该 Throwable 对象存储了任务中可能抛出的异常。与`beforeExecute()`方法相同，Runnable 对象是 FutureTask 类的一个实例。
- `newTaskFor()`：该方法创建的任务将执行使用`submit()`方法发送的 Runnable 对象。该方法必须返回 RunnableFuture 接口的一个实现。默认情况下，Open JDK 9 和 Oracle JDK 9 返回 FutureTask 类的一个实例，但是这在今后的实现中可能会发生变化。如果扩展 ScheduledThreadPoolExecutor 类，可以重载`decorateTask()`方法。该方法与面向预定任务的`newTaskFor()`方法类似并且允许重载执行器所执行的任务。


### 更改一些初始化参数

可以在执行器创建之时更改一些参数以改变其行为。
- BlockingQueue<Runnable>：每个执行器均使用一个内部的 BlockingQueue 存储等待执行的任务。可以将该接口的任何实现作为参数传递。例如，更改执行器执行任务的默认顺序。
- ThreadFactory：可以指定 ThreadFactory 接口的一个实现，而且执行器将使用该工厂创建执行该任务的线程。例如，可以使用 ThreadFactory 接口创建 Thread 类的一个扩展类，保存有关任务执行时间的日志信息。
- RejectedExecutionHandler：调用`shutdown()`方法或者`shutdownNow()`方法之后，所有发送给执行器的任务都将被拒绝。可以指定 RejectedExecutionHandler 接口的一个实现管理这种情形。












