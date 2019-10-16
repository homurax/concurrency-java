package com.homurax.chapter10.news.publisher;

import com.homurax.chapter10.news.data.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.Flow.Subscriber;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConsumerData {

	private MySubscription subscription;
	private Subscriber<News> consumer;
}
