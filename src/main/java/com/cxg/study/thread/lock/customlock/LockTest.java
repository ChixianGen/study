package com.cxg.study.thread.lock.customlock;   // Administrator 于 2019/8/20 创建;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class LockTest {

    static final CASLock casLock = new CASLock();
//    static final ReentrantLock casLock = new ReentrantLock();

    public static void main(String[] args) {
        Thread.currentThread().isInterrupted();
        test1();
    }

    private static void test1() {
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
//                    doSomething();
                    doSomething1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "thread_" + i).start();
        }
    }

/*    private static void doSomething() throws InterruptedException {
        log.debug("线程【{}】开始获取锁资源。", Thread.currentThread().getName());
        boolean b = false;
        try {
            b = casLock.tryLock();
            log.debug("线程【{}】尝试获取锁资源。【{}】", Thread.currentThread().getName(), b);
            if (b) {
                TimeUnit.SECONDS.sleep(1);
            }
        } finally {
            if (b) {
                log.debug("线程【{}】释放锁资源。", Thread.currentThread().getName());
                casLock.unlock();
            }
        }
    }*/

    private static void doSomething1() throws InterruptedException {
        try {
            try {
                casLock.lock();
            } catch (GetLockException e) {
                log.debug("线程【{}】获取锁资源失败。", Thread.currentThread().getName());
            }
            TimeUnit.SECONDS.sleep(1);
        } finally {
            casLock.unlock();
        }
    }
}
