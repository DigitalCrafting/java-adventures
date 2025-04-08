package org.digitalcrafting.multithreading;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Hello";
        })
        .thenApply(result -> result + ", World!")
        .thenApply(String::toUpperCase);

        future.thenAccept(System.out::println);

        Thread.sleep(1000);
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
