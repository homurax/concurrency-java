# 使用并行流处理大规模数据集：MapCollect 模型

## 使用流收集数据

`collect()` 方法可对流的元素进行转换和分组，生成一个含有流最终结果的新数据结构。

可以使用多达三种不同的数据类型：一种输入数据类型，即来自流的输入元素的数据类型；一种中间数据类型，用于在 `collect()` 方法运行过程中存放元素；以及一种输出数据类型，它由 `collect()` 方法返回。 `collect()` 方法有两个版本。

第一个版本接收三种函数型参数。

```java
<R> R collect(Supplier<R> supplier, 
              BiConsumer<R, ? super T> accumulator, 
              BiConsumer<R, R> combiner);
```

- **Supplier 函数**：创建中间数据类型对象的函数。如果使用顺序流，该方法会被调用一次。如果使用并行流，该方法会被调用多次，而且每次都必须产生一个新对象。
- **Accumulator 函数**：处理输入元素，并且在中间数据结构中存放该元素。
- **Combiner 函数**：将两个中间数据结构合二为一。该函数只有在处理并行流时才会被调用。



第二个版本接收一个实现 Collector 接口的对象。可以自己实现该接口，使用 `Collector.of()` 静态方法更容易。

```java
<R, A> R collect(Collector<? super T, A, R> collector);

// Collector.java
<T, R> Collector<T, R, R> of(Supplier<R> supplier, 
                             BiConsumer<R, T> accumulator, 
                             BinaryOperator<R> combiner,
                             Function<A, R> finisher,
                             Characteristics... characteristics)
```

- **Supplier**：该函数创建了一个中间数据类型的对象。
- **Accumulator**：调用该函数可以处理一个输入元素，如果必要还可对该元素进行转换，并且将其存放在中间数据结构中。
- **Combiner**：调用该函数可以将两个中间数据结构合并成一个。
- **Finisher**：如果需要进行最终的转换或者计算，调用该函数可以将中间数据结构转换成最终的数据结构。
- **Characteristics**：可以使用这个最后的变量参数表明所创建的收集器的一些特征。



Java 在 Collector 工厂类中提供了一些预定义的收集器。可以通过这些收集器的静态方法获得这些收集器。

- **`averagingDouble()`、`averagingInt()` 和 `averagingLong()`**

  这些方法返回一个收集器，能够计算 double、int 或者 long 型函数的算术平均值。

- **`groupingBy()`**

  该方法返回一个收集器，能够按照其对象的某一属性对流的元素进行分组，生成一个 Map，其键为所选定属性的值，而其值为具有某一确定值的对象列表。

- **`groupingByConcurrent()`**

  和前一个方法相似，只是有两点不同。

  第一个不同点在于该方法在并行模式下比 `groupingBy()` 方法更快，但是在顺序模式下却更慢。

  第二个不同点在于 `groupingByConcurrent()` 函数是一个无序的收集器。不能保证列表中项的顺序和其在流中的顺序相同。另一方面，`groupingBy()` 收集器则能够保证排序。

- **`joining()`**

  该方法返回一个 Collector 工厂类，将输入元素串联为一个字符串。

- **`partitioningBy()`**

  该方法返回一个 Collector 工厂类，基于某个谓词的结果对输入元素进行划分。

- **`summarizingDouble()`、`summarizingInt()`和 `summarizingLong()`**

  这些方法返回一个 Collector 工厂类，计算输入元素的汇总统计值。

- **`toMap()`**

  该方法返回一个 Collector 工厂类，可以基于两个映射函数将输入元素转换为一个 Map。

- **`toConcurrentMap()`**

  与前一个类似，只是以并发方式工作。在不考虑定制归并器的情况下，`toConcurrentMap()` 只是在并行流的情况下较快。

  与 `groupingByConcurrent()` 方法一样，这也是一个无序收集器，而 `toMap()` 则采用相遇时的排序执行转换。

- **`toList()`**

  该方法返回一个 Collector 工厂类，将输入元素存放到一个列表中。

- **`toCollection()`**

  该方法能够按照相遇时的排序将输入元素累加到一个新的 Collection 工厂类。该方法接收一个创建该 Collection 的 Supplier 接口实现作为参数。

- **`maxBy()` 和 `minBy()`**

  该方法返回一个 Collector 工厂类，根据以参数传递的比较器产生最大元素和最小元素。

- **`toSet()`**

  该方法返回一个 Collector，它将输入元素存放到一个集合。


