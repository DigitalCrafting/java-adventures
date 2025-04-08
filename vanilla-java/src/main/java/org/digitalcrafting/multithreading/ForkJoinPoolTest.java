package org.digitalcrafting.multithreading;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class ForkJoinPoolTest {
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 10_000;
        private final int[] array;
        private final int start, end;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            if (length <= THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                int mid = start + length / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);

                leftTask.fork();
                long rightResult = rightTask.compute();
                long leftResult = leftTask.join();

                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        int[] data = new int[1_000_000];
        int expected = 0;
        for (int i = 0; i < data.length; i++) {
            int nextInt = ThreadLocalRandom.current().nextInt(1, 100);
            data[i] = nextInt;
            expected += nextInt;
        }

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(data, 0, data.length);

        long start = System.currentTimeMillis();
        long result = pool.invoke(task);
        long end = System.currentTimeMillis();

        System.out.println("Expected sum = " + expected);
        System.out.println("Parallel sum = " + result);
        System.out.println("Time: " + (end - start) + "ms");
    }
}
