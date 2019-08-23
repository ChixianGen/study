package com.cxg.study.thread.cuncurrent.base;   // Administrator 于 2019/8/23 创建;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * {@link java.util.concurrent.CyclicBarrier}
 */
@Slf4j
public class CyclicBarrierDemo {

    private Random random = new Random(System.currentTimeMillis());

    @Test
    public void test1() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            log.debug("当前任务已全部结束");
        });
        IntStream.rangeClosed(1, 6).forEach(i -> {
            new Thread(() -> {
                try {
                    Thread.sleep(random.nextInt(3000));
                    log.debug("线程：{}开始等待", Thread.currentThread().getName());
                    int await = cyclicBarrier.await();
                    log.debug("线程：{}结束等待，继续执行，await：{}", Thread.currentThread().getName(), await);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread_" + i).start();
        });

        Thread.sleep(10000);
    }
}