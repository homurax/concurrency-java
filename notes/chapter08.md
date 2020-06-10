# 使用并行流处理大规模数据集：MapReduce 模型

Java 8 引入的最重要的创新是 lambda 表达式和流 API。流是可以以顺序方式或者并行 方式处理的一个元素序列。可以应用中间操作转换流，然后执行最终计算以获得预期结果。



## 流的简介

流就是一个数据序列（并不是一种数据结构），可以以顺序方式或者并发方式应用某一操作序列来筛选、转换、排序、约简（reduce）或组织这些元素，以获得某一最终对象。



### 流的基本特征

- 流并不存储其元素。
- 可以以并行方式处理流而无须做任何额外工作。`stream()`、`parallelStream()` 。
- 流不可重用。
- 流可对数据做延迟处理。
- 不能以不同方式访问流的元素。流操作通常对元素做统一处理，因此有的只有元素本身。无法知道元素在流中的位置及其相邻元素。
- 流操作并不允许修改流的源。



### 流的组成部分

- 生成供流使用的数据的来源。
- 0 个或者多个中间操作，这些操作产生另一个流作为输出。
- 生成对象的末端操作，该对象可以是一个简单对象，也可以是一个类似数组、列表或者哈希表的 Collection。也可以存在不产生任何显式结果的末端操作。



1. 流的来源

   流的来源可产生将由 Stream 对象处理的数据。可从多个数据源创建一个流。

   Collection 接口包括了生成顺序流的 `stream()` 方法，以及生成并行流的 `parallelStream()` 方法。

   Array 类含有四种版本可从数组产生流的 `stream()` 方法。

   Stream 接口提供了 `generate()` 方法和 `iterate()` 方法。

2. 中间操作

   中间操作最重要的特征在于它将另一个流作为结果返回。输入流和输出流的对象可以是不同类型的，但是中间操作总可以生成新流。

   `distinct()`、`filter()`、`flatMap()`、`limit()`、`map()`、`peek()`、`skip()`、`sorted()`

3. 末端操作

   末端操作将某个对象作为结果返回，而绝不会返回一个流。一般来说，所有流都会以一个末端操作结束，而该末端操作返回的是整个操作序列的最终结果。

   `collect`、`count`、`max`、`min`、`reduce`、`forEach`、`forEachOrdered`、`findFirst`、`findAny`、`anyMatch`、`allMatch`、`noneMatch`、`toArray`



### 约简操作

- `reduce(accumulator)`

  将 accumulator 函数应用于流的所有元素。在这种情况下没有初始值。它返回一个含有 accumulator 函数最终结果的 Optional 对象，或者当该流为空时返回一个空的 Optional 对象。

  accumulator 函数必须是一个 associative 函数，它实现了 BinaryOperator 接口。两个参数既可以是流元素，也可以是之前调用 accumulator 函数所返回的部分结果。

- `reduce(identity, accumulator)`

  当最终结果和流的元素类型相同时，必须采用该版本。标识值必须为 accumulator 函数的标识值。也就是说，如果将 accumulator 函数应用于标识值和任意值 V，必须返回同样的值 `V: accumulator(identity, V) = V`。

  该标识值用作 accumulator 函数的第一个结果，如果流没有元素，则该值作为返回值。accumulator 必须是一个实现 BinaryOperator 接口的 associative 函数。

- `reduce(identity, accumulator, combiner)`

  当最终结果与流的元素为不同类型时，必须使用该版本。标识值必须是 combiner 函数的标识。也就是说，`combiner(identity, v) = v`。
  这里的 combiner 函数必须与 accumulator 函数兼容，即 `combiner(u, accumulator(identity, v)) = accumulator(u, v)`。
  accumulator 函数采用局部结果和流的下一个元素生成另一个局部结果。combiner 函数采用两个局部结果来生成另一个局部结果。
  这两个函数必须均是 associative 函数，但是在这种情况下，accumulator 函数是 BiFunction 接口的实现，而 combiner 函数是 BinaryOperator 接口的实现。



`reduce()` 方法存在局限。

accumulator 函数每处理一个元素都会返回一个新值。如果 accumulator 函数处理的是集合，那么每当它处理一个元素时都会创建一个新的集合，这样效率就很低。

另一个问题是，如果采用并行流，那么所有的线程都要共享标识值。

如果该值是一个可变对象，例如一个 Collection，那么所有的线程都将作用于相同的 Collection 之上。这样就有悖于 `reduce()` 操作的初衷了。

此外，`combiner()` 方法总是接收两个相同的 Collection （所有的线程仅作用于一个 Collection 之上）作为参数，这也有悖于 `reduce()` 操作的初衷。

