package org.digitalcrafting.multithreading;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

public class PhaserExample {
    public static void main(String[] args) {
        int workerCount = 3;
        Phaser phaser = new Phaser(workerCount);

        for (int i = 0; i < workerCount; i++) {
            final int workerId = i;
            new Thread(() -> {
                for (int phase = 0; phase < 3; phase++) {
                    System.out.println("Worker " + workerId + " doing phase " + phase);
                    sleep(ThreadLocalRandom.current().nextInt(500, 1500));
                    System.out.println("Worker " + workerId + " done phase " + phase + ". Waiting for others... ");
                    phaser.arriveAndAwaitAdvance(); // wait for others
                }
                System.out.println("Worker " + workerId + " done.");
            }).start();
        }
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}
