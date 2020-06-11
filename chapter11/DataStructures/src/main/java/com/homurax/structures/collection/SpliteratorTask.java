package com.homurax.structures.collection;

import com.homurax.structures.hash.data.Product;

import java.util.Spliterator;

public class SpliteratorTask implements Runnable {

    private final Spliterator<Product> spliterator;

    public SpliteratorTask(Spliterator<Product> spliterator) {
        this.spliterator = spliterator;
    }

    @Override
    public void run() {
        int counter = 0;
        while (spliterator.tryAdvance(product -> product.setTitle(product.getTitle().toLowerCase()))) {
            counter++;
        }
        System.out.println(Thread.currentThread().getName() + ":" + counter);
    }

}
