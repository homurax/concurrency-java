package com.homurax.newsNotification.publisher;

import com.homurax.newsNotification.data.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Flow.Subscriber;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsumerData {

    private Subscriber<News> consumer;
    private MySubscription subscription;

}
