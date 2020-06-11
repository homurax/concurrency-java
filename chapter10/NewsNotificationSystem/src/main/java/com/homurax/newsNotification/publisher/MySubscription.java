package com.homurax.newsNotification.publisher;

import lombok.Setter;

import java.util.Set;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicLong;

public class MySubscription implements Subscription {

    private boolean cancelled = false;
    private final AtomicLong requested = new AtomicLong(0);
    @Setter
    private Set<Integer> categories;

    @Override
    public void request(long value) {
        requested.addAndGet(value);
    }

    @Override
    public void cancel() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public long getRequested() {
        return requested.get();
    }

    public void decreaseRequested() {
        requested.decrementAndGet();
    }

    public boolean hasCategory(int category) {
        return categories.contains(category);
    }

    @Override
    public String toString() {
        return "MySubscription{" +
                "cancelled=" + cancelled +
                ", requested=" + requested +
                ", categories=" + categories +
                '}';
    }
}
