package com.homurax.eventNotification.consumer;

import com.homurax.eventNotification.data.Event;

import java.util.Random;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.TimeUnit;

public class Consumer implements Subscriber<Event> {

    private final String name;
    private Subscription subscription;

    public Consumer(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
        this.showMessage("Subscription OK");
    }

    @Override
    public void onNext(Event event) {
        this.showMessage("An event has arrived: " + event.getSource() + ": " + event.getDate() + ": " + event.getMsg());
        this.subscription.request(1);
        processEvent(event);
    }

    @Override
    public void onError(Throwable error) {
        this.showMessage("An error has occurred");
        error.printStackTrace();
    }

    @Override
    public void onComplete() {
        this.showMessage("No more events");
    }


    private void showMessage(String txt) {
        System.out.println(Thread.currentThread().getName() + ":" + this.name + ": " + txt);
    }

    private void processEvent(Event event) {
        Random random = new Random();
        int number = random.nextInt(3);
        try {
            TimeUnit.SECONDS.sleep(number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
