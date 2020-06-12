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



**Deque**

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

**变量句柄（variable handle）**是一种对变量、静态域或数组元素的动态型引用，使之可以用多种不同的模式访问该变量。

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











