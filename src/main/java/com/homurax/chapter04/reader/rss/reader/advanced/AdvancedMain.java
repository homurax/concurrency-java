package com.homurax.chapter04.reader.rss.reader.advanced;

import java.util.concurrent.TimeUnit;

public class AdvancedMain {

	public static void main(String args[]) {

		NewsAdvancedSystem system = new NewsAdvancedSystem("src\\main\\java\\com\\homurax\\chapter04\\reader\\data\\sources.txt");
		Thread thread = new Thread(system);
		thread.start();

		try {
			TimeUnit.MINUTES.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		system.shutdown();
	}

}
