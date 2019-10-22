package com.homurax.chapter12.information.phaser;

import com.homurax.chapter12.information.common.CommonPhaserTask;

import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainPhaser {

    public static void main(String[] args) {

        Phaser phaser = new Phaser(10);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            executor.execute(new CommonPhaserTask(phaser));
        }

        for (int i = 0; i < 20; i++) {
			printPhaser(phaser);
			try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
		printPhaser(phaser);
	}

	private static void printPhaser(Phaser phaser) {
		System.out.println("Arrived Parties: " + phaser.getArrivedParties());
		System.out.println("Unarrived Parties: " + phaser.getUnarrivedParties());
		System.out.println("Phaser: " + phaser.getPhase());
		System.out.println("Registered Parties: " + phaser.getRegisteredParties());
		System.out.println("Terminated: " + phaser.isTerminated());
		System.out.println("*******************************************");
	}
}
