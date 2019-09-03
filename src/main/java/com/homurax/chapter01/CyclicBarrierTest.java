package com.homurax.chapter01;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.concurrent.CyclicBarrier;

@AllArgsConstructor
@NoArgsConstructor
public class CyclicBarrierTest extends Thread {

    private CyclicBarrier cyclicBarrier;

    @Override
    public void run() {
        try {
            sleep(1000);
            System.out.println(getName() + " arrive barrier");
            cyclicBarrier.await();
            sleep(1000);
            System.out.println(getName() + " crossed barrier");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 栅栏 每个任务都必须等到所有任务都到达同步点后才能继续执行
    public static void main(String[] args) {
        int parties = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties, () -> System.out.println("barrierAction"));

        for (int i = 0; i < parties * 2; i++) {
            new CyclicBarrierTest(cyclicBarrier).start();
        }
    }


}
