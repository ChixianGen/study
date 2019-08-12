package com.cxg.study.thread.designpattern.countdown;   // Administrator 于 2019/7/25 创建;

import java.util.Random;
import java.util.stream.IntStream;

public class Test {
    private final static Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    private static void test1() throws InterruptedException {
        CountDownLaunchDemo latch = new CountDownLaunchDemo(21);
        IntStream.rangeClosed(0,20).forEach(i -> {
            new Thread(() -> {
                try {
                    Thread.sleep(RANDOM.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.printf("进入countDown同步块的线程：【%s】。\n", Thread.currentThread().getName());
            }, "t_" + i).start();
        });
        latch.await();

        System.out.println("=======================");
    }
}
