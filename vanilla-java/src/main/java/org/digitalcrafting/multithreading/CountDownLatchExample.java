package org.digitalcrafting.multithreading;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(() -> {
            try {
                System.out.println("Producer: working...");
                Thread.sleep(500);
                latch.countDown();
                System.out.println("Producer: done, latch released");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                System.out.println("Consumer: waiting...");
                latch.await();
                System.out.println("Consumer: proceeding!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }
}
