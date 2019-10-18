package com.homurax.chapter11.synchronization.barrier;

import java.time.LocalDateTime;

public class FinishBarrierTask implements Runnable {

	@Override
	public void run() {
		System.out.println("FinishBarrierTask: All the tasks have finished");
		try {
			System.out.println(LocalDateTime.now());
			Thread.sleep(10000);
			System.out.println(LocalDateTime.now());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
