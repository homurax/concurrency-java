package com.homurax.chapter10.news.publisher;

import com.homurax.chapter10.news.data.News;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.ThreadPoolExecutor;

public class MyPublisher implements Publisher<News> {

    private ConcurrentLinkedDeque<ConsumerData> consumers;
    private ThreadPoolExecutor executor;

    public MyPublisher() {
        this.consumers = new ConcurrentLinkedDeque<>();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void subscribe(Subscriber<? super News> subscriber) {

        MySubscription subscription = new MySubscription();
        subscriber.onSubscribe(subscription);

        ConsumerData consumerData = new ConsumerData(subscription, (Subscriber<News>) subscriber);
        consumers.add(consumerData);
    }

    public void publish(News news) {
        consumers.forEach(consumerData -> {
            try {
                executor.execute(new PublisherTask(consumerData, news));
            } catch (Exception e) {
                consumerData.getConsumer().onError(e);
            }
        });
    }

    public void shutdown() {
        consumers.forEach(consumerData -> consumerData.getConsumer().onComplete());
        executor.shutdown();
    }
}
