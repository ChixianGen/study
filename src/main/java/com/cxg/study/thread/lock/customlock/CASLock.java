package com.cxg.study.thread.lock.customlock;   // Administrator 于 2019/8/20 创建;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CASLock {

    // 默认为0，没上锁状态；
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void lock() throws GetLockException {
        log.debug("线程【{}】开始尝试锁资源。", Thread.currentThread().getName());
        if (!atomicInteger.compareAndSet(0, 1)) {
            throw new GetLockException(Thread.currentThread().getName() + "-->获取锁失败");
        }
        log.debug("线程【{}】获得了锁资源。", Thread.currentThread().getName());
    }

    /**
     * 多线程情况下，会有问题；如：A线程释放了B线程上的锁；
     */
    public void unlock() {
        int c = atomicInteger.get();
        log.debug("线程【{}】开始释放锁资源。当前值为【{}】", Thread.currentThread().getName(), c);
        if (c == 0) {
            log.debug("线程【{}】直接返回。", Thread.currentThread().getName());
            return;
        }
        boolean b = atomicInteger.compareAndSet(1, 0);
        log.debug("线程【{}】释放了锁资源，【{}】，当前值为【{}】", Thread.currentThread().getName(), b, atomicInteger.get());
    }
}
