package org.digitalcrafting.multithreading;

public class ThreadsExample {
    private static final Object lock = new Object();
    private static boolean ready = false;

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Producer: preparing data...");
                sleep(500);
                ready = true;
                lock.notify();
                System.out.println("Producer: data ready, notified.");
            }
        });

        Thread consumer = new Thread(() -> {
            synchronized (lock) {
                while (!ready) {
                    try {
                        System.out.println("Consumer: waiting for data...");
                        lock.wait();
                    } catch (InterruptedException ignored){};
                }
                System.out.println("Consumer: got the data!");
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
