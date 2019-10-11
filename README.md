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
- [Class Phaser](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Phaser.html)

### Fork/Join 框架

- ForkJoinPool：该类实现了要用于运行任务的执行器。
- ForkJoinTask：这是一个可以在ForkJoinPool 类中执行的任务。
- ForkJoinWorkerThread：这是一个准备在ForkJoinPool 类中执行任务的线程。

### 并发数据结构 

阻塞型数据结构：这些数据结构含有一些能够阻塞调用任务的方法，例如，当数据结构为空而又要从中获取值时。  
非阻塞型数据结构：如果操作可以立即进行，它并不会阻塞调用任务。否则，它将返回null值或者抛出异常。  

- [ConcurrentLinkedDeque](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentLinkedDeque.html)：非阻塞型的列表。
- [ConcurrentLinkedQueue](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentLinkedQueue.html)：非阻塞型的队列。
- [LinkedBlockingDeque](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedBlockingDeque.html)：阻塞型的列表。
- [LinkedBlockingQueue](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/LinkedBlockingQueue.html)：阻塞型的队列。
- [PriorityBlockingQueue](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/PriorityBlockingQueue.html)：基于优先级对元素进行排序的阻塞型队列。
- [ConcurrentSkipListMap](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentSkipListMap.html)：非阻塞型的 NavigableMap。
- [ConcurrentHashMap](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentHashMap.html)：非阻塞型的哈希表。
- AtomicBoolean、AtomicInteger、AtomicLong 和 AtomicReference：基本Java数据类型的原子实现。

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

### 4.1.1 任务的撤销

使用`submit()`方法将 Runnable 对象发送给执行器时，它会返回 Future 接口的一个实现。
该类允许控制该任务的执行。该类有`cancel()`方法，可用于撤销任务的执行。
该方法接收一个布尔值作为参数，如果接收到的参数为 true ，那么执行器执行该任务，否则执行该任务的线程会被中断。

以下是想要撤销的任务无法被撤销的情形。
- 任务已经被撤销。
- 任务已经完成了执行。
- 任务正在执行而提供给`cancel()`方法的参数为 false。
- 在API文档中并未说明的其他原因

`cancel()`方法返回了一个布尔值，用于表明当前任务是否被撤销。


### 4.1.2 任务执行调度

Java并发API为 ThreadPoolExecutor 类提供了一个扩展类，以支持预定任务的执行，这就是 ScheduledThreadPoolExecutor 类。
- 在某段延迟之后执行某项任务。
- 周期性地执行某项任务，包括以固定速率执行任务或者以固定延迟执行任务。


### 4.1.3 重载执行器方法

可以通过扩展一个已有的类（ThreadPoolExecutor 或者 ScheduledThreadPoolExecutor）实现自己的执行器，获得想要的行为。
这些类中包括一些便于改变执行器工作方式的方法。如果重载了 ThreadPoolExecutor 类，就可以重载以下方法。
- `beforeExecute()`：该方法在执行器中的某一并发任务执行之前被调用。它接收将要执行的 Runnable 对象和将要执行这些对象的 Thread 对象。该方法接收的 Runnable 对象是 FutureTask 类的一个实例，而不是使用`submit()`方法发送给执行器的 Runnable 对象。
- `afterExecute()`：该方法在执行器中的某一并发任务执行之后被调用。它接收的是已执行的 Runnable 对象和一个 Throwable 对象，该 Throwable 对象存储了任务中可能抛出的异常。与`beforeExecute()`方法相同，Runnable 对象是 FutureTask 类的一个实例。
- `newTaskFor()`：该方法创建的任务将执行使用`submit()`方法发送的 Runnable 对象。该方法必须返回 RunnableFuture 接口的一个实现。默认情况下，Open JDK 9 和 Oracle JDK 9 返回 FutureTask 类的一个实例，但是这在今后的实现中可能会发生变化。如果扩展 ScheduledThreadPoolExecutor 类，可以重载`decorateTask()`方法。该方法与面向预定任务的`newTaskFor()`方法类似并且允许重载执行器所执行的任务。


### 4.1.4 更改一些初始化参数

可以在执行器创建之时更改一些参数以改变其行为。
- BlockingQueue<Runnable>：每个执行器均使用一个内部的 BlockingQueue 存储等待执行的任务。可以将该接口的任何实现作为参数传递。例如，更改执行器执行任务的默认顺序。
- ThreadFactory：可以指定 ThreadFactory 接口的一个实现，而且执行器将使用该工厂创建执行该任务的线程。例如，可以使用 ThreadFactory 接口创建 Thread 类的一个扩展类，保存有关任务执行时间的日志信息。
- RejectedExecutionHandler：调用`shutdown()`方法或者`shutdownNow()`方法之后，所有发送给执行器的任务都将被拒绝。可以指定 RejectedExecutionHandler 接口的一个实现管理这种情形。

### 4.1.5 有关执行器的其他信息
- `shutdown()`：必须显式调用该方法以结束执行器的执行，也可以重载该方法，加入一些代码释放执行器所使用的额外资源。
- `shutdownNow()`：`shutdown()`方法和`shutdownNow()`方法之间的区别在于`shutdown()`方法要等待执行器中所有处于等待状态的任务全部终结。
- `submit()`、`invokeall()`或者`invokeany()`：可以调用这些方法向执行器发送并发任务。如果需要在将任务插入到执行器任务队列之前或之后进行一些操作，就可以重载这些方法。在任务进行排队之前或之后添加定制操作与在该任务执行之前或之后添加定制操作是不同的，这些操作要考虑到重载`beforeExecute()`方法和`afterExecute()`方法。
- `schedule()`：该方法在给定延迟之后执行某个任务，且该任务仅执行一次。
- `scheduleAtFixedRate()`：该方法按照给定周期执行一个周期性任务。它与`scheduleWithFixedDelay()`方法的区别在于，对于后者而言，两次执行之间的延迟是指第一次执行结束之后到第二次执行之前的时间；而对于前者而言，两次执行之间的延迟是指两次执行起始之间的时间。



# Chapter05 从任务获取数据：Callable 接口与 Future 接口


## 5.1 Callable 接口和 Future 接口

执行器框架允许执行并发任务而无须创建和管理线程。可以创建任务并将其发送给执行器，而执行器负责创建和管理所需的线程。
- 基于`Runnable`接口的任务：这些任务实现了不返回任何结果的`run()`方法。
- 基于`Callable`接口的任务：这些任务实现了返回某个对象作为结果的`call()`接口。`call()`方法返回的具体类型由`Callable`接口的泛型参数指定。为了获取该任务返回的结果，执行器会为每个任务返回一个`Future`接口的实现。


### 5.1.1 Callable 接口

主要特征如下。
- 它是一个通用接口。它有一个简单类型参数，与`call()`方法的返回类型相对应。
- 声明了`call()`方法。执行器运行任务时，该方法会被执行器执行。它必须返回声明中指定类型的对象。
- `call()`方法可以抛出任何一种校验异常。可以实现自己的执行器并重载`afterExecute()`方法来处理这些异常。


### 5.1.2 Future 接口

向执行器发送一个 Callable 任务时，它将返回一个 Future 接口的实现，这允许控制任务的执行和任务状态，使你能够获取结果。该接口的主要特征如下。

- 使用`cancel()`方法来撤销任务的执行。该方法有一个布尔型参数，用于指定是否需要在任务运行期间中断任务。
- 校验任务是否已被撤销（采用`isCancelled()`方法）或者是否已经结束（采用`isDone()`方法）。
- 你可以使用`get()`方法获取任务返回的值。当任务完成执行后，将返回任务所返回的值。如果任务并没有完成执行，它将挂起执行线程直到任务执行完毕。`get(long, TimeUnit)`方法带有两个参数：时间周期和该周期的 TimeUnit 。该方法区别在于将线程等待的时间周期作为参数来传递。如果这一周期结束后任务仍未结束执行，该方法就会抛出一个`TimeoutException`异常。


## 5.2 其他相关方法

关于 AbstractExecutorService 接口的方法。

- `invokeAll (Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)`：当作为参数传递的 Callable 任务列表中的所有任务完成执行，或者执行时间超出了第二、第三个参数指定的时间范围时，该方法返回一个与该 Callable 任务列表相关联的 Future 对象列表。
- `invokeAny (Collection<? Extends Callable<T>> tasks, long timeout, TimeUnit unit)`：当作为参数传递的 Callable 任务列表中的任务在超时（由第二和第三个参数指定的期限）之前完成其执行并且没有抛出异常时，该方法返回 Callable 任务列表中第一个任务的结果。如果超时，那么该方法抛出一个 TimeoutException 异常。

关于 [CompletionService](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletionService.html) 接口的方法。
已有的实现类 [ExecutorCompletionService](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorCompletionService.html)
- `poll()`方法：检索并且删除自上一次调用`poll()`或`take()`方法以来下一个已完成任务的 Future 对象。如果没有任何任务完成，执行该方法将返回 null 值。
- `take()`方法：该方法和前一个方法类似，只不过如果没有任何任务完成，它将休眠该线程，直到有一个任务执行完毕为止。



# Chapter06 运行分为多阶段的任务：Phaser 类


在并发 API 中，最重要的因素就是它为编程人员提供的同步机制。**同步**是指为获得预期结果而对两个或多个任务进行的协调。 


## 6.1 Phaser 类简介

[Phaser](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Phaser.html) 类是一种同步机制，用于控制以并发方式划分为多个阶段的算法的执行。
如果处理过程已有明确定义的步骤，那么必须在开始第二个步骤之前完成第一步的工作，以此类推，并且可以使用 Phaser 类实现该过程的并发版本。  
Phaser 类的主要特征有以下几点。
- 分段器（phaser）必须知道要控制的任务数。Java 称之为参与者的注册机制。参与者可以随时在分段器中注册。
- 任务完成一个阶段之后必须通知分段器。在所有参与者都完成该阶段之前，分段器将使该任务处于休眠状态。
- 在内部，分段器保存了一个整数值，该值存储分段器已经进行的阶段变更数目。
- 参与者可以随时脱离分段器的控制。Java 将这一过程称为参与者的注销。
- 当分段器做出阶段变更时，可以执行定制的代码。
- 控制分段器的终止。如果一个分段器终止了，就不再接受新的参与者，也不会进行任务之间的同步。
- 通过一些方法获得分段器的参与者数目及其状态。


### 6.1.1 参与者的注册与注销

正常情况下，参与者在执行开始时注册，但是也可以随时注册。可以采用不同方式注册参与者，如下所示。
- 创建 Phaser 对象时：Phaser 类提供了四个不同的构造函数。其中常用的有两个。
    - `Phaser()`：该构造函数创建了一个，具有0个参与者的分段器。
    - `Phaser(int parties)`：该构造函数创建了一个含有给定数目参与者的分段器。
- 还可以通过下述方法显式创建。
    - `bulkRegister(int parties)`：同时注册给定数目的新参与者。
    - `register()`：注册一个新参与者。

分段器控制的任务完成执行时，必须从分段器注销。如果不这样做，分段器就会在下一阶段变更中一直等待该任务。
注销一个参与者，可以使用`arriveAndDeregister()`方法。使用该方法告知分段器该任务已经完成了当前阶段，而且不再参与下一阶段。


### 6.1.2 同步阶段变更

分段器的主要目的是使那些可以分割成多个阶段的算法以并发方式执行。所有任务完成当前阶段之前，任何任务都不能进入下一阶段。  
Phaser 类提供了`arrive()`、 `arriveAndDeregister()`和`arriveAndAwaitAdvance()`三个方法通报任务已经完成当前阶段。  
如果其中某个任务没有调用上述三个方法之一，那么分段器对其他参与任务的阻塞是不确定的。继续进入下一阶段需要用到下述方法。  
- `arriveAndAwaitAdvance()`：任务使用该方法向分段器通报，表明它已经完成了当前阶段并且要继续下一阶段。分段器将阻塞该任务，直到所有参与的任务已调用其中一个同步方法。
- `arrive()`：通知分段器当前阶段已经完成，但是不会等待剩下的任务（使用该方法时要非常小心）。
- `arriveAndDeregister()`：告知分段器当前阶段已经完成，而且并不想在分段器中继续等待（通常是因为已经完成了任务）。
- `awaitAdvance(int phase)`：任务使用该方法向分段器通报，如果该方法参数中的数值和分段器的实际阶段数相等，就要等待当前阶段结束；如果这两个数值不相等，则该方法立即返回。

### 6.1.3 其他功能

在所有参与任务都完成了某个阶段的执行之后，在继续下一阶段之前，Phaser 类执行`onAdvance(int phase, int registeredParties)`方法。该方法接收如下两个参数。
- phase：这是已执行完毕阶段的编号。第一个阶段的编号为0。
- registeredParties：这个参数代表参与任务的数目。

如果想在两个阶段之间执行一些代码，例如，对某些数据进行排序或者转换，那么可以扩展 Phaser 类并重载该方法以实现自己的分段器。

分段器可以有以下两种状态。
- 激活状态：创建了分段器且新的参与者注册后，分段器将进入激活状态，并持续这种状态，直到其终止。处于这种状态时，它接受新的参与者并像之前所述那样工作。
- 终止状态：`onAdvance()`方法返回 true 值时，分段器进入这种状态。默认情况下，当所有参与者都注销后，`onAdvance()`方法将返回 true 值。

分段器处于终止状态时，新参与者的注册无效，而且同步方法会立即返回。

Phaser 类提供了一些方法，获取分段器状态和其中参与者的信息。
- `getRegisteredParties()`：该方法返回分段器中参与者的数目。
- `getPhase()`：该方法返回当前阶段的编号。
- `getArrivedParties()`：该方法返回已经完成当前阶段的参与者的数目。
- `getUnarrivedParties()`：该方法返回尚未完成当前阶段的参与者的数目。
- `isTerminated()`：如果分段器处于终止状态，则该方法返回 true 值，否则返回 false 值。



# Chapter07 优化分治解决方案：Fork/Join 框架

Java 7 并发 API 通过 Fork/Join 框架引入了一种特殊的执行器。该框架的设计目的是针对那些可以使用分治设计范式来解决的问题，实现最优的并发解决方案。


## 7.1 Fork/Join 框架简介

框架基于 [ForkJoinPool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html) 类，该类是一种特殊的执行器，具有`fork()`方法和`join()`方法两个操作（以及它们的不同重载），以及一个被称作**工作窃取**算法的内部算法。


### 7.1.1 Fork/Join 框架的基本特征

Fork/Join 框架用于解决基于分治方法的问题。必须将原始问题划分为较小的问题，直到问题很小，可以直接解决。有了这个框架，待实现任务的主方法便如下所示：

``` java
if (problem.size() > DEFAULT_SIZE) {
    divideTasks();
    executeTask();
    taskResults = joinTasksResult();
    return taskResults;
} else {
    taskResults = solveBasicProblem();
    return taskResults;
}

//
if (problem.size() > DEFAULT_SIZE) {
    childTask1 = new Task();
    childTask2 = new Task();
    childTask1.fork();
    childTask2.fork();
    childTaskResults1 = childTask1.join();
    childTaskResults2 = childTask2.join();
    taskResults = makeResults(childTaskResults1, childTaskResults2);
    return taskResults;
} else {
    taskResults = solveBasicProblem();
    return taskResults;
}
```

- `fork()`：该方法可以将一个子任务发送给 Fork/Join 执行器。
- `join()`：该方法可以等待一个子任务执行结束后返回其结果。

工作窃取算法确定要执行的任务。当一个任务使用`join()`方法等待某个子任务结束时，执行该任务的线程将会从任务池中选取另一个等待执行的任务并且开始执行。  
通过这种方式，Fork/Join 执行器的线程总是通过改进应用程序的性能来执行任务。

Java 8 在Fork/Join 框架中提供了一种新特性。现在，每个 Java 应用程序都有一个默认的 ForkJoinPool，称作公用池。
可以通过调用静态方法`ForkJoinPool.commonPool()`获得这样的公用池，而不需要采用显式方法创建（尽管可以这样做）。
这种默认的 Fork/Join 执行器会自动使用由计算机的可用处理器确定的线程数。
可以通过更改系统属性值`java.util.concurrent.ForkJoinPool.common.parallelism`来修改这一默认行为。


### 7.1.2 Fork/Join 框架的局限性

- 不再进行细分的基本问题的规模既不能过大也不能过小。按照 Java API 文档的说明，该基本问题的规模应该介于100到10 000个基本计算步骤之间。
- 数据可用前，不应使用阻塞型 I/O 操作，例如读取用户输入或者来自网络套接字的数据。这样的操作将导致 CPU 核资源空闲，降低并行处理等级，进而使性能无法达到最佳。
- 不能在任务内部抛出校验异常，必须编写代码来处理异常（例如，陷入未经校验的 RuntimeException）。对于未校验异常有一种特殊的处理方式。


### 7.1.3 Fork/Join 框架的组件

Fork/Join 框架包括四个基本类。
- [ForkJoinPool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html) ：该类实现了 Executor 接口和 ExecutorService 接口，而执行 Fork/Join 任务时将用到 Executor 接口。Java 提供了一个默认的 ForkJoinPool 对象（公用池）。如果需要，还可以创建一些构造函数。可以指定并行处理的等级（运行并行线程的最大数目）。默认情况下，它将可用处理器的数目作为并发处理等级。
- [ForkJoinTask](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinTask.html) ：这是所有 Fork/Join 任务的基本抽象类。该类是一个抽象类，提供了`fork()`方法和`join()`方法。该类还实现了 Future 接口，提供了一些方法来判断任务是否以正常方式结束，它是否被撤销，或者是否抛出了一个未校验异常。RecursiveTask 类、RecursiveAction 类和 CountedCompleter 类提供了`compute()`抽象方法。为了执行实际的计算任务，该方法应该在子类中实现。
- [RecursiveTask](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveTask.html) ：该类扩展了 ForkJoinTask 类。RecursiveTask 也是一个抽象类，而且应该作为实现返回结果的 Fork/Join 任务的起点。
- [RecursiveAction](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveAction.html) ：该类扩展了 ForkJoinTask 类。RecursiveAction 类也是一个抽象类，而且应该作为实现不返回结果的 Fork/Join 任务的起点。
- [CountedCompleter](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CountedCompleter.html) ：该类扩展了 ForkJoinTask 类。该类应作为实现任务完成时触发另一任务的起点。


## 7.2 Fork/Join 框架的其他方法

使用 ForkJoinPool 类的`execute()`方法和`invoke()`方法将任务发送给池。还可以使用另一个名为`submit()`的方法。

它们之间的主要区别在于：
- `execute()`方法将任务发送给 ForkJoinPool 之后立即返回一个 void 值。
- `invoke()`方法将任务发送给 ForkJoinPool 后，当任务完成执行后方可返回；
- `submit()`方法将任务发送给 ForkJoinPool 之后立即返回一个 Future 对象，用以控制任务的状态并且获得其结果。

ForkJoinTask 类为`invoke()`方法提供了一种替代方案，即`quietlyInvoke()`方法。这两种方法的主要区别在于，`invoke()`方法返回任务执行的结果或者在必要时抛出异常，而`quietlyInvoke()`方法不返回任务的结果，也不抛出任何异常。后者与示例中用到的`quietlyJoin()`方法相似。


# Chapter08 使用并行流处理大规模数据集： MapReduce 模型

**此章节主要介绍 Stream API 的使用，比较熟悉的话可以不看。**

[Interface BaseStream](https://docs.oracle.com/javase/8/docs/api/java/util/stream/BaseStream.html)
[Interface Stream](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html)


# Chapter09 使用并行流处理大规模数据集： MapCollect 模型

**此章节主要介绍如何处理流，更加关注`collect()`末端操作，比较熟悉的话可以不看。**

[Interface Collector](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collector.html)
[Class Collectors](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html)


## 9.1 collect() 方法

`collect()`方法可对流的元素进行转换和分组，生成一个含有流最终结果的新数据结构。可以使用多达三种不同的数据类型：一种输入数据类型，即来自流的输入元素的数据类型；一种中间数据类型，用于在`collect()`方法运行过程中存放元素；以及一种输出数据类型，它由`collect()`方法返回。


``` java
<R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);
```
此方法用到了两种不同的数据类型：来自流的元素的输入数据类型，以及用于存放中间元素并返回最终结果的中间数据类型。

- Supplier：这是一个创建中间数据类型对象的函数。如果使用顺序流，该方法会被调用一次。如果使用并行流，该方法会被调用多次，而且每次都必须产生一个新对象。
- Accumulator：调用该函数可以处理输入元素，如果必要还可对该元素进行转换，并且将其存放在中间数据结构中。
- Combiner：调用该函数可以将两个中间数据结构合二为一。该函数只有在处理并行流时才会被调用。

此处 Combiner 是 BiConsumer，它必须将第二个中间结果合并到第一个中间结果中。



``` java
<R, A> R collect(Collector<? super T, A, R> collector);
```
此方法接收一个实现 Collector 接口的对象。你可以自己实现该接口，使用`Collector.of()`静态方法更容易。Java 在 Collector 工厂类中提供了一些预定义的收集器，也可以通过这些收集器的静态方法获得这些收集器。

Collector 中定义了如下方法
``` java
Supplier<A> supplier();
BiConsumer<A, T> accumulator();
BinaryOperator<A> combiner();
Function<A, R> finisher();
Set<Characteristics> characteristics();
```

- Supplier：这是一个创建中间数据类型对象的函数。如果使用顺序流，该方法会被调用一次。如果使用并行流，该方法会被调用多次，而且每次都必须产生一个新对象。
- Accumulator：调用该函数可以处理输入元素，如果必要还可对该元素进行转换，并且将其存放在中间数据结构中。
- Combiner：调用该函数可以将两个中间数据结构合二为一。该函数只有在处理并行流时才会被调用。
- Finisher：如果需要进行最终的转换或者计算，调用该函数可以将中间数据结构转换成最终的数据结构。
- Characteristics：可以使用这个最后的变量参数表明所创建的收集器的一些特征。

此处 Combiner 是 BinaryOperator，而且应该返回该 Combiner。因此既可以选择将第二个中间结果合并到第一个，也可以将第一个中间结果合并到第二个，或者也可以创建一个新的中间结果。


Collector 中定义了枚举类 Characteristics，用于表示收集器属性的特性，可用于优化归约实现。
- CONCURRENT：此收集器是并发的，这意味着结果容器可以支持与来自多个线程的同一结果容器并发调用的 accumulator 。
- UNORDERED：此收集操作不保证保留输入元素的顺序。
- IDENTITY_FINISH：表明 finisher 是标识功能，可以省略。

若想要收集的最终结果和容器是一样的，比如收集的最终结果是集合，toList 收集器，就属于这种情况。  
此时，finisher 方法不需要对容器做任何操作。更正式地说，此时的 finisher 方法其实是 identity 函数：它返回传入参数的值。如果这样，收集器就展现出**`IDENTITY_FINISH`**的特征，需要使用 characteristics 方法声明。



