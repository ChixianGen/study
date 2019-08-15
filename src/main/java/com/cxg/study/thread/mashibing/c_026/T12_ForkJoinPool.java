package com.cxg.study.thread.mashibing.c_026;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * ForkJoinPool中用于执行任务的线程是守护线程；
 * {@link java.util.concurrent.ForkJoinPool #registerWorker(java.util.concurrent.ForkJoinWorkerThread)}
 */
public class T12_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();
    static Vector<Long> count = new Vector<>();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
//        System.out.println(Arrays.stream(nums).sum()); //stream api
    }

    /**
     * 无返回值的任务
     */
    static class AddTask_1 extends RecursiveAction {

        int start, end;

        AddTask_1(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) sum += nums[i];
//                System.out.println("from:" + start + " to:" + end + " = " + sum);
                count.add(sum);
            } else {

                int middle = start + (end - start) / 2;

                AddTask_1 subTask1 = new AddTask_1(start, middle);
                AddTask_1 subTask2 = new AddTask_1(middle, end);
                subTask1.fork();
                subTask2.fork();
            }
        }
    }

    /**
     * 有返回值
     */
    static class AddTask extends RecursiveTask<Long> {

        private static final long serialVersionUID = 1L;
        int start, end;

        AddTask(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) sum += nums[i];
                return sum;
            }

            int middle = start + (end - start) / 2;

            AddTask subTask1 = new AddTask(start, middle);
            AddTask subTask2 = new AddTask(middle, end);
            subTask1.fork();
            subTask2.fork();
            return subTask1.join() + subTask2.join();
        }
    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();
        return_task(fjp);
        no_return_task(fjp);
    }

    private static void no_return_task(ForkJoinPool fjp) {
        AddTask_1 task = new AddTask_1(0, nums.length);
        fjp.execute(task);
        try {
            // 阻塞main线程，不然main线程退出，任务就无法执行完；
            TimeUnit.SECONDS.sleep(1);
            long sum = count.stream().mapToLong(n -> n).sum();
            System.out.printf("no_return_task --> [%s]\n", sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void return_task(ForkJoinPool fjp) {
        AddTask task = new AddTask(0, nums.length);
        fjp.execute(task);
        long result = task.join();
        System.out.printf("is_return_task --> [%s]\n", result);
    }
}
