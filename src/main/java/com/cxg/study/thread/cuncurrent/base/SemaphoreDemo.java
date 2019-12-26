package com.cxg.study.thread.cuncurrent.base;   // Administrator 于 2019/8/26 创建;

import com.cxg.study.utils.SleepUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * {@link Semaphore}
 */
@Slf4j
public class SemaphoreDemo {

    public static void main(String[] args) {
//        test1(1);
        test2();
    }

    private static void test2() {
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        final Semaphore semp = new Semaphore(5);
        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int NO = index;
            exec.execute(() -> {
                try {
                    // 获取许可
                    semp.acquire();
                    System.out.println("Accessing: " + NO);
                    SleepUtils.workingByBlock((long) Math.random() * 3000);
                    //availablePermits()指还剩多少个许可
                    System.out.println("-----------------" + semp.availablePermits());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 访问完后，释放
                    semp.release();
                }
            });
        }
        // 退出线程池
        exec.shutdown();
    }

    /**
     * 当permit许可证为1时可作为lock来使用；
     *
     * @param permits
     */
    private static void test1(int permits) {
        final Semaphore semaphore = new Semaphore(permits);
        for (int i = 0; i < 5; i++) {
            new Thread("t_" + i) {
                @Override
                public void run() {
                    String name = currentThread().getName();
                    log.debug("thread: {} come in...", name);
                    try {
                        semaphore.acquire();
                        // 模拟任务；
                        log.debug("thread: {} get the lock...", name);
                        SleepUtils.workingByBlock(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        log.debug("thread: {} release the lock...", name);
                        semaphore.release();
                    }
                }
            }.start();
        }
    }
}
