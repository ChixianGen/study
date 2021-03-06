/**
 * 线程池的概念
 * nasa
 */
package com.cxg.study.thread.mashibing.c_026;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class T07_ParallelComputing {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        singleTask();
//        parallelTask();
        System.out.println(isPrime(9));
    }

    private static void parallelTask() throws ExecutionException, InterruptedException {
        // 线程池的大小建议为CPU内核的2倍；
        final int nThreads = Runtime.getRuntime().availableProcessors() * 2;
        System.out.println("nThreads --> " + nThreads);
        ExecutorService service = Executors.newFixedThreadPool(nThreads);

        MyTask t1 = new MyTask(1, 80000); //1-5 5-10 10-15 15-20
        MyTask t2 = new MyTask(80001, 130000);
        MyTask t3 = new MyTask(130001, 170000);
        MyTask t4 = new MyTask(170001, 200000);

        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);

        long start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        long end = System.currentTimeMillis();
        System.out.printf("parallelTask --> [%s]ms\n", end - start);
        service.shutdown();
    }

    private static void singleTask() {
        long start = System.currentTimeMillis();
        List<Integer> prime = getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.printf("singleTask --> [%s]ms\n", end - start);
        prime.stream().forEach(System.out::println);
    }

    static class MyTask implements Callable<List<Integer>> {
        int startPos, endPos;

        MyTask(int s, int e) {
            this.startPos = s;
            this.endPos = e;
        }

        @Override
        public List<Integer> call() throws Exception {
            List<Integer> r = getPrime(startPos, endPos);
            return r;
        }
    }

    static boolean isPrime(int num) {
        int a = num / 2;
        System.out.println("a-->" + a);
        for (int i = 2; i <= a; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) results.add(i);
        }
        return results;
    }
}
