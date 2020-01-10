package com.cxg.study.thread.cuncurrent.base;   // Administrator 于 2019/8/23 创建;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * {@link java.util.concurrent.CyclicBarrier}
 */
@Slf4j
public class CyclicBarrierDemo {

    private static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        test1();
    }

    public static void test1() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6, () -> {
            log.debug("当前任务已全部结束");
        });
        IntStream.rangeClosed(1, 6).forEach(i -> {
            new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(3000));
                    log.debug("线程：{}开始等待-1", Thread.currentThread().getName());
                    int await = cyclicBarrier.await();
                    log.debug("线程：{}结束等待-1，继续执行，await：{}", Thread.currentThread().getName(), await);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(random.nextInt(3000));
                    log.debug("线程：{}开始等待-2", Thread.currentThread().getName());
                    int await = cyclicBarrier.await();
                    log.debug("线程：{}结束等待-2，继续执行，await：{}", Thread.currentThread().getName(), await);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(random.nextInt(3000));
                    log.debug("线程：{}开始等待-3", Thread.currentThread().getName());
                    int await = cyclicBarrier.await();
                    log.debug("线程：{}结束等待-3，继续执行，await：{}", Thread.currentThread().getName(), await);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread_" + i).start();
        });
    }
}
