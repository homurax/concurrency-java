package com.homurax.news.rss.reader.basic;

import com.homurax.news.rss.buffer.NewsBuffer;
import com.homurax.news.rss.data.CommonInformationItem;
import com.homurax.news.rss.data.RSSDataCapturer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewsTask implements Runnable {

    private String name;

    private String url;

    private NewsBuffer buffer;

    public NewsTask(String name, String url, NewsBuffer buffer) {
        this.name = name;
        this.url = url;
        this.buffer = buffer;
    }


    @Override
    public void run() {

        System.out.println(name + ": Running. " + new Date());

        RSSDataCapturer capturer = new RSSDataCapturer(name);
        List<CommonInformationItem> items = capturer.load(url);

        for (CommonInformationItem item : items) {
            buffer.add(item);
        }
    }

}
