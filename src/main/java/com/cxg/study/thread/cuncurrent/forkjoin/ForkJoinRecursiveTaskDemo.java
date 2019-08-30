package com.cxg.study.thread.cuncurrent.forkjoin;   // Administrator 于 2019/8/30 创建;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * {@link java.util.concurrent.ForkJoinPool}
 * 执行有返回值的任务；
 */
@Slf4j
public class ForkJoinRecursiveTaskDemo {
    private final static Integer MAX = 100000;
    private final static Integer BEGIN = 1;
    private final static Integer END = 100000000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        simpleTask();
        forkJoinTask();
    }

    private static void simpleTask() {
        long begin = System.currentTimeMillis();
        int result = 0;
        for (int i = BEGIN; i <= END; i++) {
            result += i;
        }
        log.debug("simpleTask 计算结果：{}，耗时：{}", result, System.currentTimeMillis() - begin);
    }

    private static void forkJoinTask() {
        long begin = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> task = forkJoinPool.submit(new CalcRecursiveTask(BEGIN, END));
        Integer result = null;
        try {
            result = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("forkJoinTask 计算结果：{}，耗时：{}", result, System.currentTimeMillis() - begin);
    }

    @AllArgsConstructor
    static class CalcRecursiveTask extends RecursiveTask<Integer> {
        private Integer begin;
        private Integer end;

        @Override
        protected Integer compute() {
            if (end - begin <= MAX) {
                return IntStream.rangeClosed(begin, end).sum();
            } else {
                int middle = (begin + end) / 2;
                CalcRecursiveTask left = new CalcRecursiveTask(begin, middle);
                CalcRecursiveTask right = new CalcRecursiveTask(middle + 1, end);
                left.fork();
                right.fork();
                return left.join() + right.join();
            }
        }
    }
}
