package com.homurax.chapter10.news;

import com.homurax.chapter10.news.consumer.Consumer;
import com.homurax.chapter10.news.data.News;
import com.homurax.chapter10.news.publisher.MyPublisher;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

    	// Subscriber
        Set<Integer> sports = new HashSet<>();
        sports.add(News.SPORTS);
		Subscriber<News> consumer1 = new Consumer("Sport Consumer", sports);

        Set<Integer> science = new HashSet<>();
        science.add(News.SCIENCE);
		Subscriber<News> consumer2 = new Consumer("Science Consumer", science);

        Set<Integer> all = new HashSet<>();
        all.add(News.ECONOMIC);
        all.add(News.SCIENCE);
        all.add(News.SPORTS);
        all.add(News.WORLD);
		Subscriber<News> consumer3 = new Consumer("All Consumer", all);

		// Publisher
		MyPublisher publisher = new MyPublisher();
        publisher.subscribe(consumer1);
        publisher.subscribe(consumer2);
        publisher.subscribe(consumer3);

		System.out.println("Main: Start");

        News sportNews = new News();
		sportNews.setTxt("Basketball news");
		sportNews.setCategory(News.SPORTS);
		sportNews.setDate(new Date());
        publisher.publish(sportNews);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		News economicNews = new News();
		economicNews.setTxt("Money news");
		economicNews.setCategory(News.ECONOMIC);
		economicNews.setDate(new Date());
        publisher.publish(economicNews);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		News wordNews = new News();
		wordNews.setTxt("Europe news");
		wordNews.setCategory(News.WORLD);
		wordNews.setDate(new Date());
        publisher.publish(wordNews);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		News scienceNews = new News();
		scienceNews.setTxt("Space news");
		scienceNews.setCategory(News.SCIENCE);
		scienceNews.setDate(new Date());
        publisher.publish(scienceNews);

        publisher.shutdown();

		System.out.println("Main: End");
    }

}
