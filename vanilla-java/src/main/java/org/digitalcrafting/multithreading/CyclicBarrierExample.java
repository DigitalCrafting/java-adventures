package org.digitalcrafting.multithreading;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int workerCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(workerCount, () -> {
            System.out.println("All threads reached the barrier. Proceeding...");
        });

        for (int i = 0; i < workerCount; i++) {
            final int id = i;
            new Thread(() -> {
                System.out.println("Worker " + id + " doing work...");
                sleep(500);
                try {
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Worker " + id + " passed barrier.");
            }).start();
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
