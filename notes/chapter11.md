# 并发数据结构和同步工具

## 并发数据结构

### 阻塞型数据结构和非阻塞型数据结构

Java 并发 API 中提供了两种并发数据结构。

- **阻塞型数据结构**

  这种类型的数据结构提供了插入数据和删除数据的方法，当操作无法立即执行时（例如，如果你要选取某个元素但数据结构为空），执行调用的线程就会被阻塞，直到可以执行该操作为止。

- **非阻塞型数据结构**

  这种类型的数据结构提供了插入数据和删除数据的方法，当无法立即执行操作时，返回一个特定值或者抛出一个异常。

有时，非阻塞型数据结构会有一个与之等效的阻塞型数据结构。

例如，ConcurrentLinkedDeque 类是一个非阻塞型数据结构，而 LinkedBlockingDeque 类则是一个与之等效的阻塞型数据结构。



阻塞型数据结构的一些方法具有非阻塞型数据结构的行为。

例如，Deque 接口定义了 `pollFirst()` 方法，如果双端队列为空，该方法并不会阻塞，而是返回 null 值。另一方面，`getFirst()` 方法在这种情况下会抛出异常。每个阻塞型队列的实现都实现了该方法。



### 并发数据结构

Java 集合框架（Java collections framework，JCF）提供了一个包含多种可用于串行编程的数据结构集合。Java 并发 API 对这些数据结构进行了扩展，提供了另外一些可用于并发应用程序的数据结构，包括如下两项。

- **接口**：扩展了 JCF 提供的接口，添加了一些可用于并发应用程序的方法。
- **类**：实现了前面的接口，提供了可以用于应用程序的具体实现。



#### 接口

##### Queue

队列是一种线性数据结构，允许在队列的末尾插入元素且从队列的起始位置获取元素。它是一个 FIFO 型数据结构，第一个进入队列的元素将是第一个被处理的元素。

Queue 接口定义了在队列中执行的基本操作。该接口提供了实现如下操作的方法。

- 在队列的末尾插入一个元素。
- 从队列的首部开始检索并删除一个元素。
- 从队列的首部开始检索一个元素但不删除。

对于这些方法，该接口定义了两个版本。它们在方法执行时具有不同的表现。

- 可以抛出异常的方法。
- 可以返回某一特定值的方法，例如 false 或 null 。

| 操作         | 抛出异常  | 返回特殊值 |
| :----------- | :-------- | :--------- |
| 插入         | add()     | offer()    |
| 检索并删除   | remove()  | poll()     |
| 检索但不删除 | element() | peek()     |



#####  BlockingQueue

BlockingQueue 接口扩展了 Queue 接口，添加了当操作不可执行时阻塞调用线程的方法。

| 操作         | 阻塞   |
| :----------- | :----- |
| 插入         | put()  |
| 检索并删除   | take() |
| 检索但不删除 | N/A    |



##### Deque

与队列一样，双端队列也是一种线性数据结构，但是允许从该数据结构的两端插入和删除元素。

Deque 接口扩展了 Queue 接口。除了 Queue 接口提供的方法之外，它还提供了从两端执行插入、检索且删除、检索但不删除等操作的方法。

| 操作         | 抛出异常                    | 返回特定值                |
| :----------- | :-------------------------- | :------------------------ |
| 插入         | addFirst()、addLast()       | offerFirst()、offerLast() |
| 检索并删除   | removeFirst()、removeLast() | pollFirst()、pollLast()   |
| 检索但不删除 | getFirst()、getLast()       | peekFirst()、peekLast()   |



##### BlockingDeque

BlockingDeque 接口扩展了 Deque 接口，添加了当操作无法执行时阻塞调用线程的方法。

| 操作         | 阻塞                    |
| :----------- | :---------------------- |
| 插入         | putFirst()、putLast()   |
| 检索并删除   | takeFirst()、takeLast() |
| 检索但不删除 | N/A                     |



##### TransferQueue

该接口扩展了 BlockingQueue 接口，并且增加了将元素从生产者传输到消费者的方法。在这些方法中，生产者可以一直等到消费者取走其元素为止。该接口添加的新方法有如下几项。

- `transfer()`

  将一个元素传输给一个消费者，并且等待（阻塞调用线程）该元素被使用。

- `tryTransfer()`

  如果有消费者等待，则传输一个元素。否则，该方法返回 false 值，并且不将该元素插入队列。



##### ConcurrentMap

Map 接口定义了使用 map 的基本操作。ConcurrentMap 扩展了 Map 接口，为并发应用程序提供了相同的方法。

接口在 Java 8 中做了修改，包含了下述方法。

- `forEach()`

  该方法针对 map 的所有元素执行给定函数。

- `compute()`、`computeIfAbsent()` 和 `computeIfPresent()`

  这些方法允许指定一个函 数，该函数用于计算与某个键相关的新值。

- `merge()`

  该方法允许指定将某个键值对合并到某个已有的 map 中。如果 map 中没有该键，则直接插入，否则，执行指定的函数。



#### 类

##### LinkedBlockingQueue

该类实现了 BlockingQueue 接口，提供了一个带有阻塞型方法的队列，该方法可以有任意有限数量的元素。该类还实现了 Queue、Collection 和 Iterable 接口。



##### ConcurrentLinkedQueue

该类实现了 Queue 接口，提供了一个线程安全的无限队列。从内部来看，该类使用一种非阻塞型算法保证应用程序中不会出现数据竞争。



##### LinkedBlockingDeque

该类实现了 BlockingDeque 接口，提供了一个带有阻塞型方法的双端队列，它可以有任意有限数量的元素。

LinkedBlockingDeque 具有比 LinkedBlockingQueue 更多的功能，但是其开销更大。因此，应在双端队列特性不必要的场合使用 LinkedBlockingQueue 类。



##### ConcurrentLinkedDeque

该类实现了 Deque 接口，提供了一个线程安全的无限双端队列，它允许在双端队列的两端添加和删除元素。

它具有比 ConcurrentLinkedQueue 更多的功能，但与 LinkedBlockingDeque 相同，该类开销更大。



##### ArrayBlockingQueue

该类实现了 BlockingQueue 接口，基于一个数组提供了阻塞型队列的一个实现，可以有有限个元素。它还实现了 Queue、Collection 和 Iterable 接口。

与基于数组的非并发数据结构（ArrayList、 ArrayDeque）不同，ArrayBlockingQueue 按照构造函数中所指定的固定大小为数组分配空间，而且不可再调整其大小。



##### DelayQueue

该类实现了 BlockingDeque 接口，提供了一个带有阻塞型方法和无限数目元素的队列实现。

该队列的元素必须实现 Delayed 接口，因此它们必须实现 `getDelay()` 方法。如果该方法返回一个负值或 0，那么延时已过期，可以取出队列的元素。位于队列首部的是延时负数值最小的元素。



##### LinkedTransferQueue

该类提供了一个 TransferQueue 接口的实现。它提供了一个元素数量无限的阻塞型队列。这些元素有可能被用作生产者和消费者之间的通信信道。在那里，生产者可以等待消费者处理它们的元素。



##### PriorityBlockingQueue

该类提供了 BlockingQueue 接口的一个实现，在该类中可以按照元素的自然顺序选择元素，也可以通过该类构造函数中指定的比较器选择元素。该队列的首部由元素的排列顺序决定。



##### ConcurrentHashMap

该类提供了 ConcurrentMap 接口的一个实现。它提供了一个线程安全的哈希表。除了 Java 8 中 Map 接口新增加的方法之外，该类还增加了其他一些方法。

- `search()`、`searchEntries()`、`searchKeys()` 和 `searchValues()`

  这些方法允许对键值对、键或者值应用搜索函数。这些搜索功能可以是一个 lambda 表达式。搜索函数返回一个非空值时，该方法结束。这也是该方法的执行结果。

- `reduce()`、`reduceEntries()`、`reduceKeys()` 和 `reduceValues()`

  这些方法允许应用一个 `reduce()` 操作转换键值对、键，或者将其整个哈希表作为流处理。

- `reduceToInt()`、`reduceToDouble()`、`reduceToLong()`
- `reduceEntriesToDouble()`、`reduceEntriesToInt()`、`reduceEntriesToLong()`
- `reduceKeysToDouble()`、`reduceKeysToInt()`、`reduceKeysToLong()`
- `reduceValuesToDouble()`、`reduceValuesToInt()`、`reduceValuesToLong()`



### 原子变量

原子变量是在 Java 1.5 中引入的，用于提供针对 integer、long、boolean、reference 和 Array 对象的原子操作。它们提供了一些方法来递增值、递减值、确定值、返回值，或者在其当前值等于预定义值时确定值。原子变量提供了与 volatile 关键字相似的保障。

Java 8 中增加了四个新类，即 DoubleAccumulator、DoubleAdder、LongAccumulator 和 LongAdder。

LongAdder 与 AtomicLong 类似，但是当经常更新来自不同线程的累加操作并且只需要在操作的末端给出结果时，具有更好的性能。

- `add()`
  为计数器增加参数中指定的值。

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



### 变量句柄

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



## 同步机制

Java 语言提供的最基本的同步机制是 synchronized 关键字。该关键字可应用于某个方法或者某个代码块。对于第一种情况，一次只有一个线程可以执行该方法。对于第二种情况，要指定一个对某个对象的引用。在这种情况下，同时只能执行被某一对象保护的一个代码块。

Java 也提供了其他一些同步机制。

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



### Semaphore

信号量机制是 Edsger Dijkstra 于 1962 年提出的，用于控制对一个或多个共享资源的访问。

该机制基于一个内部计数器以及两个名为 `wait()` 和 `signal()` 的方法。

当一个线程调用了 `wait()` 方法时，如果内部计数器的值大于 0，那么信号量对内部计数器做递减操作，并且该线程获得对该共享资源的访问。

如果内部计数器的值为 0，那么线程将被阻塞，直到某个线程调用 `singal()` 方法为止。

当一个线程调用了 `signal()` 方法时，信号量将会检查是否有某些线程处于等待状态（它们已经调用了 `wait()` 方法）。

如果没有线程等待，它将对内部计数器做递增操作。如果有线程在等待信号量，就获取这其中的一个线程，该线程的 `wait()` 方法结束返回并且访问共享资源。

其他线程将继续等待，直到轮到自己为止。



在 Java 中，信号量在 Semaphore 类中实现。`wait()` 方法被称作 `acquire()`，而 `signal()` 方法被称作 `release()` 。



类似 ReentrantLock，临界段开始调用 `semaphore.acquire()` ，在 finally 代码块中调用 `semaphore.release()`。



### CountDownLatch

该类提供了一种等待一个或多个并发任务完成的机制。它有一个内部计数器，必须使用要等待的 任务数初始化。`await()` 方法休眠调用线程，直到内部计数器为 0，并且使用 `countDown()` 方法对该内部计数器做递减操作。



Task 中完成任务后调用 `countDown()`，主线程中调用 `await()` 等待，从而实现主线程中阻塞到所有 Task 完成后继续进行。

Task 中任务开始前调用 `await()` 等待，主线程中调用 `countDown()`，从而实现主线程中控制所有 Task 同时执行。



### CyclicBarrier

该类允许将一些任务同步到某个共同点。所有的任务都在该点等待，直到任务全部到达该点为止。

从内部来看，该类还管理了一个内部计数器，用于记录尚未到达该点的任务。

当一个任务到达指定点时，它要执行 `await()` 方法以等待其他任务。

当所有任务都到达时，CyclicBarrier 对象将它们唤醒，这样就能够继续执行。

当所有的参与方都到达后，该类允许执行另一个任务。为了实现这一点，要在该对象的构造方法中指定一个 Runnable 对象。



```java
public CyclicBarrier(int parties, Runnable barrierAction)
```

当 parties 个 Task 中调用了 `barrier.await()` 后，就会执行 barrierAction 。



### CompletableFuture

这是在 Java 8 并发 API 中引入的一种同步机制，在 Java 9 中又有了一些新方法。

它扩展了 Future 机制，为其赋予了更强的功能和更大的灵活性。它允许实现一个事件驱动的模型，链接那些只有当其他任务执行完毕后才执行的任务。



与 Future 接口相同，CompletableFuture 也必须采用操作要返回的结果类型进行参数化。

和 Future 对象一样，CompletableFuture 类表示的是异步计算的结果，只不过 CompletableFuture 的结果可以由任意线程确立。

当计算正常结束时，该类采用 `complete()` 方法确定结果，而当计算出现异常时，则采用 `completeExceptionally()` 方法。

如果两个或者多个线程调用同一 CompletableFuture 的 `complete()`方法或 `completeExceptionally()` 方法，那么只有第一个调用会起作用。


除了 `complete()` 方法，也可以使用 `runAsync()` 方法或者 `supplyAsync()` 创建一个任务结果。

`runAsync()` 方法执行一个 Runnable 对象并且返回 `CompletableFuture<Void>`，这样计算就不能再返回任何结果了。

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

  它将返回一个 `CompletableFuture<Void>` 对象，而该对象将在所有的 CompletableFuture 对象都完成之后返回其结果。

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

  方法接收一个 Supplier 对象作为参数（还可以选择 Executor）。借助 Supplier 的结果完成  CompletableFuture。

- `orTimeout()`

  方法接收一段时延。如果 CompletableFuture 在这段时间之后没有完成，那么抛出 TimeoutException 异常并异常完成。

- `completeOnTimeout()`

  方法与上一个方法相似，只不过它在作为参数的值的范围内正常完成。

- `delayedExecutor()`

  方法返回一个 Executor，该执行器在执行指定时延之后执行某一任务。



