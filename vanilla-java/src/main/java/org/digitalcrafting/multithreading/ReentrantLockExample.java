package org.digitalcrafting.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static boolean ready = false;

    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Producer: preparing data...");
                Thread.sleep(500);
                ready = true;
                condition.signal();
                System.out.println("Producer: data ready, notified.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        Thread consumer = new Thread(() -> {
            lock.lock();
            try {
                while (!ready) {
                    System.out.println("Consumer: waiting for data...");
                    condition.await();
                }
                System.out.println("Consumer: got the data!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        consumer.start();
        producer.start();

        producer.join();
        consumer.join();
    }
}
