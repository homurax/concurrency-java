# 异步流处理：反应流

反应流为带有非阻塞回压（back pressure）的异步流处理定义了标准。

这类系统最大的问题是资源消耗。快速的生产者会使较慢的消费者超负荷。这些组件之间的数据队列规模可能过度增加，从而影响整个系统的行为。回压机制确保了在生产者和消费者之间进行协调的队列含有限定数目的元素。



反应流定义了描述必要操作和实体所需的接口、方法和协议的最小集合。它们基于以下三个要素。

- 信息的发布者。
- 一个或多个信息订阅者。
- 发布者和消费者之间的订阅关系。



反应流规范根据以下规则明确了这些类应该如何交互。

- 发布者将添加那些希望得到通知的订阅者。
- 订阅者被发布者添加时会收到通知。
- 订阅者以异步方式请求来自发布者的一个或多个元素，也就是说，订阅者请求元素并继续其执行。
- 发布者有一个要发布的元素时，会将其发送给请求元素的所有订阅者。

所有这些通信都是异步的，因此可以充分利用多核处理器的全部性能。



Java 9 包含了三个接口，即 Flow.Publisher、Flow.Subscriber 和 Flow.Subscription， 以及一个实用工具类，SubmissionPublisher 类。它们可支持实现反应流应用程序。



## Java 反应流简介

- **Flow.Publisher 接口**

  描述了条目的生产者。

- **Flow.Subscriber 接口**

  描述了条目的使用者，即消费者。

- **Flow.Subscription 接口**

  描述了生产者与消费者之间的连接。实现该接口的类可以管理生产者和消费者之间的条目交换。



### Flow.Publisher 接口

- `subscribe()`

  该方法接收 Flow.Subscriber 接口的一个实现作为参数，并且将该订阅者添加到其内部订阅者列表。

  该方法并不返回任何结果。从内部来看，它使用 Flow.Subscriber 接口提供的方法向订阅者发送条目、错误信息和订阅对象。

  

### Flow.Subscriber 接口

- `onSubscribe()`

  该方法由发布者调用，用于完成订阅者的订阅过程。

  它向订阅者发送了 Flow.Subscription 对象，该对象管理发布者和订阅者之间的通信。

- `onNext()`

  当发布者想把新条目发送给订阅者时，会调用该方法。

  在该方法中，订阅者必须处理该条目。该方法并不返回任何结果。

- `onError()`

  如果出现了一个不可恢复的错误，而且没有调用其他的订阅者方法，那么发布者将调用该方法。

  该方法接收 Throwable 对象作为参数，其中含有已发生的错误。

- `onComplete()`

  不再发送任何条目时，发布者将调用该方法。该方法没有参数，也不返回结果。



### Flow.Subscription 接口

- `cancel()`

  订阅者调用该方法告诉发布者它不再需要任何条目了。

- `request()`

  订阅者调用该方法来告诉发布者它需要更多的条目。它将订阅者想要的条目数作为参数。



### SubmissionPublisher 类

SubmissionPublisher 实现了 Flow.Publisher 接口。它还使用 Flow.Subscription 接口，并且提供向消费者发送条目的方法，这些方法用于了解消费者数量、发布者和消费者之间的订阅关系，以及关闭它们之间的通信。下面给出了该类比较重要的方法。

- `subscribe()`

  该方法由 Flow.Publisher 接口提供，用于向发布者订阅一个 Flow.Subscriber 对象。

- `offer()`

  该方法以异步方式调用其 `onNext()` 方法，向每个订阅者发布一个条目。

- `submit()`

  该方法以异步方式调用其 `onNext()`方法，向每个订阅者发布一个条目。资源对任何订阅者都不可用时，进行不间断阻塞。

- `estimateMaximumLag()`

  该方法对发布者已生成但尚未被已订阅的订阅者使用的条目进行估计。

- `estimateMinimumDemand()`

  该方法对消费者已请求但是发布者尚未生成的条目数进行估计。

- `getMaxBufferCapacity()`

  该方法返回每个订阅者的最大缓冲区。

- `getNumberOfSubscribers()`

  该方法返回订阅者的数量。

- `hasSubscribers()`
  该方法返回一个布尔值，该值用于指示发布者是否有订阅者。

- `close()`

  该方法调用当前发布者的所有订阅者的 `onComplete()` 方法。

- `isClosed()`

  该方法返回一个布尔值，用于指示当前发布者是否已关闭。



## 面向事件通知的集中式系统

### ★ 小结

Subscriber：实现相关方法（完成订阅、消费、异常处理）的逻辑。持有 Subscription，订阅者与生产者之前通过 Subscription 交互（要求更多的项目或者不再需要）。

向 SubmissionPublisher 中注册订阅者，Task 持有 SubmissionPublisher，通过 `submit()` 方法发送项目。可以启动多个 Task 来进行并发。



## 新闻系统

如果 SubmissionPublisher 提供的功能不符合需求，那么必须实现自己的发布者和订阅关系。

每则新闻将与一个类别相关联。订阅者将订阅一个或多个类别，而发布者只会向每个订阅相应类别的订阅者发送新闻。



### ★ 小结

Subscriber：持有 subscription、categories。

Subscription：持有 categories，使用 AtomicLong 记录请求条目数量。

ConsumerData：封装一组 Subscriber 和 Subscription。

Publisher：持有 consumers 和 executor。

Task：实现 Runnable。完成判断、发布者把新条目发送给订阅者（ `Subscriber.onNext()` ）、请求数目减少的逻辑。



