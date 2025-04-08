package org.digitalcrafting.multithreading;

import java.util.concurrent.*;

public class ExecutorExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Callable<String> task = () -> {
            Thread.sleep(500);
            return "Task completed by " + Thread.currentThread().getName();
        };

        Future<String> future = executor.submit(task);

        System.out.println("Before future.get()");
        String result = future.get(); // Blocks until done
        System.out.println(result);
        System.out.println("After future.get()");

        executor.shutdown();
    }
}
