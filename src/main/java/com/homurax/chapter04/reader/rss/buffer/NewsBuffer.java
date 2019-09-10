package com.homurax.chapter04.reader.rss.buffer;

import com.homurax.chapter04.reader.rss.data.CommonInformationItem;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class NewsBuffer {

    private LinkedBlockingQueue<CommonInformationItem> buffer;

    private ConcurrentHashMap<String, String> storedItems;

    public NewsBuffer() {
        this.buffer = new LinkedBlockingQueue<>();
        this.storedItems = new ConcurrentHashMap<>();
    }

    public void add(CommonInformationItem item) {
        storedItems.compute(item.getId(), (id, oldSource) -> {
            if (oldSource == null) {
                buffer.add(item);
                return item.getSource();
            } else {
                System.out.println("Item " + item.getId() + " has been processed before");
                return oldSource;
            }
        });
    }

    public CommonInformationItem get() throws InterruptedException {
        return buffer.take();
    }
}
