# 并发设计原理

## 基本的并发概念

### 并发与并行

**并行**：系统中同时运行了多个任务。

**并发**：将任务和它们对共享资源的访问同步的不同技术和机制的方法。

并发和并行是非常相似的概念，而且这种相似性随着多核处理器的发展也在不断增强。



### 同步

同步可以理解为一种协调两个或更多任务以获得预期结果的机制。

- **控制同步**

  当一个任务的开始依赖于另一个任务的结束时，第二个任务不能在第一个任务完成之前开始。

- **数据访问同步**

  当两个或更多任务访问共享变量时，在任意时间里，只有一个任务可以访问该变量。



**临界段**是一段代码，由于它可以访问共享资源，因此在任何给定时间内，只能够被一个任务执行。**互斥**是用来保证这一要求的机制，而且可以采用不同的方式来实现。



流行的同步机制：

- **信号量（semaphore）**

  一种用于控制对一个或多个单位资源进行访问的机制。它有一个用于存放可用资源数量的变量，并且可以采用两种原子操作来管理该变量的值。互斥（mutex，mutual exclusion）是一种特殊类型的信号量，它只能取两个值（即资源空闲和资源忙）， 而且只有将互斥设置为忙的那个进程才可以释放它。互斥可以通过保护临界段来避免出现竞争条件。

- **监视器**

  一种在共享资源之上实现互斥的机制。它有一个互斥、一个条件变量、两种操作（等待条件和通报条件）。一旦通报了该条件，在等待它的任务中只有一个会继续执行。



如果共享数据的所有用户都受到同步机制的保护，那么代码（或方法、对象）就是**线程安全**的。数据的非阻塞的 **CAS**（compare-and-swap）原语是不可变的，这样就可以在并发应用程序中使用该代码而不会出任何问题。



### 原子操作和原子变量

**原子操作**是一种发生在瞬间的操作。在并发应用程序中，可以通过一个临界段来实现原子操作，以便对整个操作采用同步机制。

**原子变量**是一种通过原子操作来设置和获取其值的变量。可以使用某种同步机制来实现一个原子变量，或者也可以使用 CAS 以无锁方式来实现一个原子变量，而这种方式并不需要任何同步机制。



## 并发应用程序中可能出现的问题

### 数据竞争

如果有两个或者多个任务在临界段之外对一个共享变量进行写入操作，也就是说没有使用任何同步机制，那么应用程序可能存在**数据竞争**（也叫作**竞争条件**）。



### 死锁

当多个任务正在等待必须由另一线程释放的某个共享资源，而该线程又正在等待必须由前述任务之一释放的另一共享资源时，并发应用程序就出现了死锁。当系统中同时出现如下四种条件时，就会导致这种情形。一般将其称为 Coffman 条件。

- 互斥

  死锁中涉及的资源必须是不可共享的。一次只有一个任务可以使用该资源。

- 占有并等待条件

  一个任务在占有某一互斥的资源时又请求另一互斥的资源。当它在等待时， 不会释放任何资源。

- 不可剥夺

  资源只能被那些持有它们的任务释放。

- 循环等待

  任务 1 正等待任务 2 所占有的资源， 而任务 2 又正在等待任务 3 所占有的资源， 以此类推，最终任务 n 又在等待由任务 1 所占有的资源，这样就出现了循环等待。

避免死锁的机制：

- 忽略

  假设自己的系统绝不会出现死锁，而如果发生死锁， 结果就是只能停止应用程序并且重新执行它。

- 检测

  系统中有一项专门分析系统状态的任务，可以检测是否发生了死锁。如果它检测到了死锁，可以采取一些措施来修复该问题，例如，结束某个任务或者强制释放某一资源。

- 预防

  预防 Coffman 条件中的一条或多条出现。

- 规避

  当一个任务要开始执行时，可以对系统中空闲的资源和任务所需的资源进行分析， 这样就可以判断任务是否能够开始执行。



### 活锁

如果系统中有两个任务，它们总是因对方的行为而改变自己的状态，那么就出现了活锁。最终结果是它们陷入了状态变更的循环而无法继续向下执行。

例如，有两个任务：任务 1 和任务 2，它们都需要用到两个资源：资源 1 和资源 2。假设任务 1 对资源 1 加了一个锁，而任务 2 对资源 2 加了一个锁。当它们无法访问所需的资源时，就会释放自己的资源并且重新开始循环。这种情况可以无限地持续下去，所以这两个任务都不会结束自己的执行过程。



### 资源不足

当某个任务在系统中无法获取维持其继续执行所需的资源时，就会出现资源不足。当有多个任务在等待某一资源且该资源被释放时，系统需要选择下一个可以使用该资源的任务。如果系统中没有设计良好的算法，那么系统中有些线程很可能要为获取该资源而等待很长时间。

要解决这一问题就要确保公平原则。所有等待某一资源的任务必须在某一给定时间之内占有该资源。



### 优先权反转

当一个低优先权的任务持有了一个高优先级任务所需的资源时，就会发生优先权反转。这样的话， 低优先权的任务就会在高优先权的任务之前执行。



## 设计并发算法的方法论

### 起点：算法的一个串行版本

算法的串行版本有两个方面的好处。

- 可以使用串行算法来测试并发算法是否生成了正确的结果。当接收同样的输入时，这两个版本的算法必须生成同样的输出结果，这样就可以检测并发版本中的一些问题，例如数据竞争或者类似的条件。
- 可以度量这两个算法的吞吐量，以此来观察使用并发处理是否能够改善响应时间或者提升算法一次性所能处理的数据量。



### 第 1 步：分析

分析算法的串行版本来寻找它的代码中有哪些部分可以以并行方式执行，应该特别关注那些执行过程花费时间最多或者执行代码较多的部分，因为实现这些部分的并发版本将能获得较大的性能改进。



### 第 2 步：设计

代码的变化将影响应用程序的两个主要部分。

- 代码的结构。
- 数据结构的组织。

完成方式。

- **任务分解**

  将代码划分成两个或多个可以立刻执行的独立任务。 其中有些任务可能必须按照某种给定的顺序来执行，或者必须在同一点上等待。使用同步机制来实现这样的行为。

- **数据分解**

  使用同一任务的多个实例分别对数据集的一个子集进行处理。该数据集是一个共享资源，如果这些任务需要修改数据，必须实现一个临界段来保护对数据的访问。



实现一个算法的并发版本，其目标在于实现性能的改善，因此应该使用所有可用的处理器或核。另一方面，当采用某种同步机制时，就引入了一些额外的必须执行的指令。

如果将算法分割成很多小任务（细粒度），实现同步机制所需额外引入的代码就会导致性能下降。如果将算法分割成比核数还少的任务（粗粒度），那么就没有充分利用全部资源。

同样还要考虑每个线程都必须要做的工作，尤其是当实现细粒度解决方案时。如果某个任务的执行时间比其他任务长，那么该任务将决定整个应用程序的执行时间。需要在这两点之间找到平衡。



### 第 3 步：实现



### 第 4 步：测试



### 第 5 步：调整

一些指标可用来评估通过使算法并行处理可能获得的性能改进。

- **加速比（speedup）**：用于评价并行版算法和串行版算法之间相对性能改进情况的指标。

  Speedup = T(sequential) / T(concurrent)

- **Amdahl 定律**：该定律用于计算对算法并行化处理之后可获得的最大期望改进。

  Speedup ≤ 1 / ((1 - P) + P / N)

  P 是可以进行并行化处理的代码的百分比，N 是准备用于执行该算法的计算机的核数。

- **Gustafson-Barsis 定律**：：Amdahl 定律具有一定缺陷。它假设当你增加核的数量时输入数据集是相同的，但是一般来说，当拥有更多的核时，就想处理更多的数据。Gustafson 定律认为， 当有更多可用的核时，可同时解决的问题规模就越大。

  Speedup = P - α × (P - 1)

  N 为核数，P 为可并行处理代码所占的百分比。



### 结论

首先，并非每一个算法都可以进行并行化处理。例如，如果要执行一个循环，其每次迭代的结果取决于前一次迭代的结果，那么就不能对该循环进行并行化处理。基于同样的原因，递归算法是无法进行并行化处理的另一个例子。

对性能良好的串行版算法实现并行处理，实际上是个糟糕的出发点。 如果在开始对某个算法进行并行化处理时，发现并不容易找到代码的独立部分，那么就要找一找该算法的其他版本，并且验证一下该版本的算法是否能够很方便地进行并行化处理。

当实现一个并发应用程序时，必须要考虑下面几点。

- **效率**

  并行版算法花费的时间必须比串行版算法少。对算法进行并行处理的首要目标就是实现运行时间比串行版算法少，或者说它能够在相同时间内处理更多的数据。

- **简单**

  必须尽可能确保其简单。它应该更加容易实现、测试、调试和维护，这样就会少出错。

- **可移植性**

- **伸缩性**



## Java 并发 API

### 基本并发类

- **Thread 类**：该类描述了执行并发 Java 应用程序的所有线程。
- **Runnable 接口**：Java 中创建并发应用程序的另一种方式。
- **ThreadLocal 类**：该类用于存放从属于某一线程的变量。
- **ThreadFactory 接口**：实现 Factory 设计模式的基类，可以用它来创建定制线程。



### 同步机制

Java 并发 API 包括多种同步机制，可以用来：

- 定义用于访问某一共享资源的临界段。
- 在某一共同点上同步不同的任务。



最重要的同步机制。

- **synchronized 关键字**

  synchronized 关键字用来在某个代码块或者某个完整的方法中定义一个临界段。 

- **Lock 接口**

  Lock 提供了比 synchronized 关键字更为灵活的同步操作。Lock 接口有多种 不同类型：ReentrantLock 用于实现一个可与某种条件相关联的锁；ReentrantReadWriteLock 将读写操作分离开来；StampedLock 是 Java 8 中增加的一种新特性，它包括三种控制读/写访问的模式。

- **Semaphore 类**

  该类通过实现经典的信号量机制来实现同步。Java 支持二进制信号量和一般信号量。

- **CountDownLatch 类**

  该类允许一个任务等待多项操作的结束。

- **CyclicBarrier 类**

  该类允许多线程在某一共同点上进行同步。

- **Phaser 类**

  该类允许控制那些分割成多个阶段的任务的执行。在所有任务都完成当前阶段之前，任何任务都不能进入下一阶段。



### 执行器

执行器框架是在实现并发任务时将线程的创建和管理分割开来的一种机制。不必担心线程的创建和管理，只需要关心任务的创建并且将其发送给执行器。该框架中涉及的主要类如下。

- **Executor 接口和 ExecutorService 接口**

  包含了所有执行器共有的 `execute()` 方法。

- **ThreadPoolExecutor 类**

  该类允许获取一个含有线程池的执行器，而且可以定义并行任务的最大数目。

- **ScheduledThreadPoolExecutor 类**

  一种特殊的执行器，可以在某段延迟之后执行任务或者周期性执行任务。

- **Executors**

  该类使执行器的创建更为容易。

- **Callable 接口**

  这是 Runnable 接口的替代接口——可返回值的一个单独的任务。

- **Future 接口**

  该接口包含了一些能获取 Callable 接口返回值并且控制其状态的方法。



### Fork/Join 框架

Fork/Join 框架定义了一种特殊的执行器，尤其针对采用分治方法进行求解的问题。

针对解决这类问题的并发任务，它还提供了一种优化其执行的机制。

Fork/Join 是为细粒度并行处理量身定制的，因为它的开销非常小，这也是将新任务加入队列中并且按照队列排序执行任务的需要。

该框架涉及的主要类和接口如下。

- ForkJoinPool：该类实现了要用于运行任务的执行器。
- ForkJoinTask：这是一个可以在 ForkJoinPool 类中执行的任务。
- ForkJoinWorkerThread：这是一个准备在 ForkJoinPool 类中执行任务的线程。



### 并行流

**流**和 **lambda 表达式**可能是 Java 8 中最重要的两个新特性。流已经被增加为 Collection 接口和其他一些数据源的方法，它允许处理某一数据结构的所有元素、生成新的结构、筛选数据和使用 MapReduce 方法来实现算法。

并行流是一种特殊的流，它以一种并行方式实现其操作。使用并行流时涉及的最重要的元素如下。

- **Stream 接口**

- **Optional**
- **Collectors**
- **lambda 表达式**



### 并发数据结构

Java 并发 API 中含有大量可以在并发应用中使用而没有风险的数据结构。分为以下两大类别。

- **阻塞型数据结构**

  这些数据结构含有一些能够阻塞调用任务的方法，例如，当数据结构为空而又要从中获取值时。

- **非阻塞型数据结构**

  如果操作可以立即进行，它并不会阻塞调用任务。否则，它将返回 null 值或者抛出异常。

其中的一些数据结构。

- ConcurrentLinkedDeque：非阻塞型的列表。
- ConcurrentLinkedQueue：非阻塞型的队列。
- LinkedBlockingDeque：阻塞型的列表。
- LinkedBlockingQueue：阻塞型的队列。
- PriorityBlockingQueue：基于优先级对元素进行排序的阻塞型队列。
- ConcurrentSkipListMap：非阻塞型的 NavigableMap。
- ConcurrentHashMap：非阻塞型的哈希表。
- AtomicBoolean、AtomicInteger、AtomicLong 和 AtomicReference：基本 Java 数据类型的原子实现。



## 并发设计模式

### 信号模式

这种设计模式介绍了如何实现某一任务向另一任务通告某一事件的情形。

实现这种设计模式最简单的方式是采用信号量或者互斥，使用 Java 语言中的 ReentrantLock 类或 Semaphore 类即可，甚至可以采用 Object 类中的 `wait()` 方法和 `notify()` 方法。

```java
public void task1() {
    section1();
    commonObject.notify();
}

public void task2() {
    commonObject.wait();
    section2();
}
```

在上述情况下，`section2()` 方法总是在 `section1()` 方法之后执行。



### 会和模式

这种设计模式是信号模式的推广。在这种情况下，第一个任务将等待第二个任务的某一事件，而第二个任务又在等待第一个任务的某一事件。其解决方案和信号模式非常相似，只不过在这种情况下， 必须使用两个对象而不是一个。

```java
public void task1() {
    section1_1();
    commonObject1.notify();
    commonObject2.wait();
    section1_2();
}

public void task2() {
    section2_1();
    commonObject2.notify();
    commonObject1.wait();
    section2_2();
}
```

在上述情况下，`section2_2()` 方法总是会在 `section1_1()` 方法之后执行，而 `section1_2()` 方法总是会在 `section2_1()` 方法之后执行。

仔细想想就会发现，如果将对 `wait()` 方法的调用放在对 `notify()` 方法的调用之前，那么就会出现死锁。



### 互斥模式

互斥这种机制可以用来实现临界段，确保操作相互排斥。

这就是说，一次只有一个任务可以执行由互斥机制保护的代码片段。在 Java 中，可以使用 synchronized 关键字（这允许你保护一段代码或者一个完整的方法）、ReentrantLock 类或者 Semaphore 类来实现一个临界段。

```java
public void task() {
    preCriticalSection();
    try {
        lockObject.lock() // 临界段开始
        criticalSection();
    } catch (Exception e) {
    } finally {
        lockObject.unlock(); // 临界段结束
        postCriticalSection();
    }
}
```



### 多元复用模式

多元复用设计模式是互斥机制的推广。

在这种情形下，规定数目的任务可以同时执行临界段。这很有用，例如，当拥有某一资源的多个副本时。在 Java 中实现这种设计模式最简单的方式是使用 Semaphore 类，并且使用可同时执行临界段的任务数来初始化该类。

```java
public void task() {
    preCriticalSection();
    semaphoreObject.acquire();
    criticalSection();
    semaphoreObject.release();
    postCriticalSection();
}
```



### 栅栏模式

这种设计模式解释了如何在某一共同点上实现任务同步的情形。

每个任务都必须等到所有任务都到达同步点后才能继续执行。Java 并发 API 提供了 CyclicBarrier 类，它是这种设计模式的一个实现。

```java
public void task() {
    preSyncPoint();
    barrierObject.await();
    postSyncPoint();
}
```



### 双重检查锁定模式

```java
public class Singleton {
    private static class LazySingleton {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getSingleton() {
        return LazySingleton.INSTANCE;
    }
}
```



### 读-写锁模式

这种模式定义了一种特殊的锁，它含有两个内部锁：一个用于读操作，而另一个用于写操作。该锁的行为特点如下所示。

- 如果一个任务正在执行读操作而另一任务想要进行另一个读操作，那么另一任务可以进行该操作。
- 如果一个任务正在执行读操作而另一任务想要进行写操作，那么另一任务将被阻塞，直到所有的读取方都完成操作为止。
- 如果一个任务正在执行写操作而另一任务想要执行另一操作（读或者写），那么另一任务将被阻塞，直到写入方完成操作为止。



### 线程池模式

这种设计模式试图减少为执行每个任务而创建线程所引入的开销。

该模式由一个线程集合和一个待执行的任务队列构成。线程集合通常具有固定大小。当一个线程完成了某个任务的执行时，它本身并不会结束执行，它要寻找队列中的另一个任务。如果存在另一个任务，那么它将执行该任务。如果不存在另一个任务，那么该线程将一直等待，直到有任务插入队列中为止，但是线程本身不会被终结。 

Java 并发 API 包含一些实现 ExecutorService 接口的类，该接口内部采用了一个线程池。



### 线程局部存储模式

这种设计模式定义了如何使用局部从属于任务的全局变量或静态变量。

当在某个类中有一个静态属性时，那么该类的所有对象都会访问该属性的同一存在。如果使用了线程局部存储，则每个线程都会访问该变量的一个不同实例。

Java 并发 API 包含了 ThreadLocal 类，该类实现了这种设计模式。



## 设计并发算法的提示和技巧

### 正确识别独立任务

执行那些相互独立的并发任务。



### 在尽可能高的层面上实施并发处理

高层次的并发处理对象都经过了优化，可以比直接使用线程提供更好的性能。往往也含有一些高级特性，可以使 API 更加强大。



### 考虑伸缩性

当使用数据分解来设计并发算法时 ，不要预先假定应用程序要在多少个核或者处理器上执行。

要动态获取系统的有关信息（例如，`Runtime.getRuntime().availableProcessors()`），并且让算法使用这些信息来计算它要执行的任务数。



### 使用线程安全 API 

### 绝不要假定执行顺序

### 在静态和共享场合尽可能使用局部线程变量

### 寻找更易于并行处理的算法版本

### 尽可能使用不可变对象

### 通过对锁排序来避免死锁

有两个任务 T1 和 T2，它们都需要两项资源 R1 和 R2，可以强制它们首先请求 R1 资源然后请求 R2 资源，这样就不会发生死锁。

```java
public void operation1() {
    lock1.lock();
    lock2.lock();
}

public void operation2() {
    lock1.lock();
    lock2.lock();
}
```



### 使用原子变量代替同步

原子变量都是在单个变量上支持原子操作的类。

它们含有一个 `compareAndSet(oldValue, newValue)` 的方法，该方法具有一种机制，可用于探测某个步骤中将新值赋给变量的操作是否完成。

如果变量的值等于 oldValue，那么该方法将变量的值更改为 newValue 并且返回 true。否则，该方法返回 false。

以类似方式工作的方法还有很多，例如 `getAndIncrement()` 和 `getAndDecrement()` 等。这些方法也都是原子的。

该解决方案是免锁的，也就是说不需要使用锁或者任何同步机制，因此它的性能比任何采用同步机制的解决方案要好。



### 占有锁的时间尽可能短

### 谨慎使用延迟初始化

### 避免在临界段中使用阻塞操作

