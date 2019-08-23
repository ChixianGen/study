package com.cxg.study.thread.cuncurrent.lock.customlock;   // Administrator 于 2019/8/20 创建;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class CASLock {

    // 默认为0，没上锁状态；
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * 获取到了锁资源的线程；
     * 用于在释放锁unlock()时判断当前线程是否是得到锁的线程；
     * 解决多线程安全的一个方案；
     * 如果没有lockThread变量，可能引发的多线程安全就是：A线程unlock()时可能会释放B线程加的锁；
     */
    private volatile Thread lockThread;

    public void lock() throws GetLockException {
        log.debug("线程【{}】开始尝试锁资源。", Thread.currentThread().getName());
        if (!atomicInteger.compareAndSet(0, 1)) {
            throw new GetLockException(Thread.currentThread().getName() + "-->获取锁失败");
        }
        lockThread = Thread.currentThread();
        log.debug("线程【{}】获得了锁资源。", lockThread.getName());
    }

    public void unlock() {
        int c = atomicInteger.get();
        log.debug("线程【{}】开始释放锁资源。当前值为【{}】", Thread.currentThread().getName(), c);
        if (c == 0) {
            log.debug("线程【{}】直接返回。", Thread.currentThread().getName());
            return;
        }

        // 加个判断防止线程安全；原因看变量定义那里；
        if (lockThread == Thread.currentThread()) {
            boolean b = atomicInteger.compareAndSet(1, 0);
            log.debug("线程【{}】释放了锁资源，【{}】，当前值为【{}】", Thread.currentThread().getName(), b, atomicInteger.get());
        } else {
            log.debug("线程【{}】不是锁资源的持有线程，无法释放锁资源", Thread.currentThread().getName());
        }
    }
}
