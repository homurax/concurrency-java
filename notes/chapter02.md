# Thread 和 Runnable

## Java 中的线程

Java 使用 Thread 类实现执行线程。

- 扩展 Thread 类并重载 `run()` 方法。
- 实现 Runnable 接口，并将该类的对象传递给 Thread 对象的构造函数。

通常更推荐第二种。

- Runnable 是一个接口：可以实现其他接口并扩展其他类。对于采用Thread类的方式，只能扩展这一个类。
- 可以通过线程来执行 Runnable 对象，但也可以通过其他类似执行器的 Java 并发对象来执行。这样可以更灵活地更改并发应用程序。
- 可以通过不同线程使用同一 Runnable 对象。

### Java 中的线程：特征和状态

Java 中的所有线程都有一个优先级，这个整数值介于 `Thread.MIN_PRIORITY` 和 `Thread.MAX_ PRIORITY` 之间（实际上它们的值分别是 1 和 10）。

所有线程在创建时其默认优先级都是 `Thread.NORM_PRIORITY`（5）。可以使用 `setPriority()` 方法更改 Thread 对象的优先级 （如果该操作不允许执行，它会抛出 SecurityException 异常）和 `getPriority()` 方法获得 Thread 对象的优先级。

对于 Java 虚拟机和线程首选底层操作系统来说，这种优先级是一种提示，而非一种契约。线程的执行顺序并没有保证。通常，较高优先级的线程将在较低优先级的线程之前执行，但是这一点并不能保证。

---

守护线程与非守护线程之间的区别在于它们如何影响程序的结束。

当有下列情形之一时，Java 程序将结束其执行过程。

- 程序执行 Runtime 类的 `exit()` 方法，而且用户有权执行该方法。
- 应用程序的所有非守护线程均已结束执行，无论是否有正在运行的守护线程。

具有这些特征的守护线程通常用在作为垃圾收集器或缓存管理器的应用程序中，执行辅助任务。 

可以使用 `isDaemon()` 方法检查线程是否为守护线程，也可以使用 `setDaemon()` 方法将某个线程seh设置为守护线程。要注意，必须在线程使用 `start()` 方法开始执行之前调用此方法。

---

线程所有可能的状态都在 `Thread.States` 类中定义。

- NEW：Thread 对象已经创建，但是还没有开始执行。
- RUNNABLE：Thread 对象正在 Java 虚拟机中运行。
- BLOCKED：Thread 对象正在等待锁定。
- WAITING：Thread 对象正在等待另一个线程的动作。
- TIME_WAITING：Thread 对象正在等待另一个线程的操作，但是有时间限制。
- THREAD：Thread 对象已经完成了执行。

在给定时间内，线程只能处于一个状态。这些状态不能映射到操作系统的线程状态，它们是 JVM 使用的状态。

### Thread 类和 Runnable 接口

Runnable 接口只定义了一种方法：`run()` 方法。这是每个线程的主方法。当执行 `start()` 方法来启动一个新线程时，它将调用 `run()` 方法。

Thread 类有很多不同的方法。它有一种 `run()` 方法，实现线程时必须重载该方法，扩展 Thread 类和必须调用的 `start()` 方法创建新的执行线程。下面给出 Thread 类的其他常用方法。

- 获取和设置 Thread 对象信息的方法。

  - `getId()`

    该方法返回 Thread 对象的标识符。该标识符是在线程创建时分配的一个正整数。在线程的整个生命周期中是唯一且无法改变的。

  - `getName()`/`setName()`：

    获取或设置 Thread 对象的名称。这个名称是一个 String 对象，也可以在 Thread 类的构造函数中建立。

  - `getPriority()`/`setPriority()`

    可以使用这两种方法来获取或设置 Thread 对象的优先级。

  - `isDaemon()`/`setDaemon()`

    这两种方法允许获取或建立 Thread 对象的守护条件。

  - `getState()`
  该方法返回 Thread 对象的状态。之前已经介绍过 Thread 对象的所有可能状态。

- `interrupt()`/`interrupted()`/`isInterrupted()`

  第一种方法表明正在请求结束执行某个 Thread 对象。另外两种方法可用于检查中断状态。

  这些方法的主要区别在于，调用 `interrupted()` 方法时将清除中断标志的值，而 `isInterrupted()` 方法不会。调用 `interrupt()` 方法不会结束 Thread 对象的执行。Thread 对象负责检查标志的状态并做出相应的响应。

- `sleep()`

  该方法将线程的执行暂停一段时间。它将接收一个 long 型值作为参数，该值代表想要 Thread 对象暂停执行的毫秒数。

- `join()`：这个方法将暂停调用线程的执行，直到被调用该方法的线程执行结束为止。可以使用该方法等待另一个 Thread 对象结束。

- `setUncaughtExceptionHandler()`：当线程执行出现未校验异常时，该方法用于建立未校验异常的控制器。

- `currentThread()`：这是 Thread 类的静态方法，它返回实际执行该代码的 Thread 对象。



## 矩阵乘法

### 公共类

MatrixGenerator 中的 `generate()` 方法用于生成矩阵。

### 串行版本

SerialMultiplier  实现了该算法的串行版本。

SerialMain 用于测试。

### 并行版本

三种粒度实现。

- 结果矩阵中每个元素对应一个线程。
- 结果矩阵中每行对应一个线程。
- 采用与可用处理器数或核心数相同的线程

#### 每个元素一个线程

IndividualMultiplierTask 实现 Runnable 接口。

ParallelIndividualMultiplier 创建所有必要的执行线程计算结果矩阵。因为如果同时启动所有线程，可能会使系统超载，所以将以 10 个线程一组的形式启动线程。启动 10 个线程后，使用 `join()` 方法等待它们完成，而且一旦完成，就启动另外 10 个线程，直到启动所有必需线程。

ParallelIndividualMain 用于测试。

#### 每行一个线程

RowMultiplierTask 实现 Runnable 接口。

ParallelRowMultiplier 类创建计算结果矩阵所需的所有执行线程。

ParallelRowMain 用于测试。

#### 线程的数量由处理器决定

获取 JVM 可用处理器数：`Runtime.getRuntime().availableProcessors()`

GroupMultiplierTask 实现 Runnable 接口。

ParallelGroupMutiplier 创建线程计算结果矩阵。

ParallelGroupMain 用于测试。

### ★ 小节

自定义 Task 类实现 Runnable 接口，`run()` 方法中编写核心计算逻辑。

循环生成 Task 实例，创建 Thread 并调用 `start()` 方法启动线程，同时将 Thread 添加进容器中。

循环 Thread 容器，调用 `join()` 方法，等待所有线程执行完毕。