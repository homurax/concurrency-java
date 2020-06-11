package com.homurax.newsNotification.main;

import com.homurax.newsNotification.consumer.Consumer;
import com.homurax.newsNotification.data.News;
import com.homurax.newsNotification.publisher.MyPublisher;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        // category
        Set<Integer> sports = Set.of(News.SPORTS);
        Set<Integer> science = Set.of(News.SCIENCE);
        Set<Integer> all = Set.of(News.ECONOMIC, News.SCIENCE, News.SPORTS, News.WORLD);

        // subscriber
        Subscriber<News> consumer1 = new Consumer("Sport Consumer", sports);
        Subscriber<News> consumer2 = new Consumer("Science Consumer", science);
        Subscriber<News> consumer3 = new Consumer("All Consumer", all);

        // publisher
        MyPublisher publisher = new MyPublisher();
        publisher.subscribe(consumer1);
        publisher.subscribe(consumer2);
        publisher.subscribe(consumer3);


        News news = new News(News.SPORTS, "Basketball news", new Date());
        publisher.publish(news);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        news = new News(News.ECONOMIC, "Money news", new Date());
        publisher.publish(news);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        news = new News(News.WORLD, "Europe news", new Date());
        publisher.publish(news);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        news = new News(News.SCIENCE, "Space news", new Date());
        publisher.publish(news);

        publisher.shutdown();
    }

}
