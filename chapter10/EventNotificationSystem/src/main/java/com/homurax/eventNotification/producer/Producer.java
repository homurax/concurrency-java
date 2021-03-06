package com.homurax.eventNotification.producer;

import com.homurax.eventNotification.data.Event;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {

    private final SubmissionPublisher<Event> publisher;
    private final String name;

    public Producer(SubmissionPublisher<Event> publisher, String name) {
        this.publisher = publisher;
        this.name = name;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Event event = new Event("Event number " + i, this.name, new Date());
            publisher.submit(event);

            int number = random.nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
