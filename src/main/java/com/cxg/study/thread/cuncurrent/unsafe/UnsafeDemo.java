package com.cxg.study.thread.cuncurrent.unsafe;   // Administrator 于 2019/8/22 创建;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class UnsafeDemo {
    public static final Unsafe unsafe;

    static {
        unsafe = getUnsafe();
    }

    private static Unsafe getUnsafe() {
//        return Unsafe.getUnsafe();
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws Exception {
        final int MAX = 2000;
        final int loop = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(MAX);
//        Counter counter = new UnSafeCounter();
//        Counter counter = new SyncCounter();
//        Counter counter = new LockCounter();
        Counter counter = new AtomicCounter();
//        Counter counter = new CASCounter();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            executorService.execute(new CountRunnable(counter, loop));
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();
        log.debug("{}--完成时间：{}ms；计算结果：{}", counter, (end - begin), counter.get());
    }
}

/**
 * 完成时间：152ms；计算结果：10000000
 */
@Slf4j
class CASCounter implements Counter {
    private long value;
    private Unsafe unsafe;
    private long offset;

    CASCounter() {
        unsafe = UnsafeDemo.unsafe;
        try {
            offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void increment() {
        long current = value;
        for (; ; ) {
//            log.debug("线程：{} 开始执行，current：{}，value：{}", Thread.currentThread().getName(), current, value);
            boolean result = unsafe.compareAndSwapLong(this, offset, current, ++current);
//            log.info("线程：{}，执行结果：{}，current：{}，value：{}", Thread.currentThread().getName(), result, current, value);
            if (result) {
                // 让某个线程让出CPU执行权给其他线程
//                try {
//                    Thread.sleep(0);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                break;
            } else {
                /**
                 * 下面代码避免CPU资源的浪费（CPU空转），去掉下面代码，看调试日志就明白了；
                 * 实际上就是跳过了for循环内的错误执行（即：一个线程把value加到很高了，轮到其他线程执行时还需要从0开始一步一步累加判断）
                  */
                current = value;
            }
        }
    }

    @Override
    public long get() {
        return value;
    }
}

/**
 * 完成时间：189ms；计算结果：10000000
 */
class AtomicCounter implements Counter {
    private AtomicLong l = new AtomicLong();

    @Override
    public void increment() {
        l.incrementAndGet();
    }

    @Override
    public long get() {
        return l.get();
    }
}

/**
 * 完成时间：252ms；计算结果：10000000
 */
class LockCounter implements Counter {
    private long i;
    private Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        try {
            lock.lock();
            i++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long get() {
        return i;
    }
}

/**
 * 完成时间：229ms；计算结果：9966020
 */
class UnSafeCounter implements Counter {
    private long i;

    @Override
    public void increment() {
        i++;
    }

    @Override
    public long get() {
        return i;
    }
}

/**
 * 完成时间：239ms；计算结果：10000000
 */
class SyncCounter implements Counter {
    private long i;

    @Override
    public synchronized void increment() {
        i++;
    }

    @Override
    public long get() {
        return i;
    }
}

interface Counter {
    void increment();

    long get();
}

@AllArgsConstructor
class CountRunnable implements Runnable {
    private Counter counter;
    private int loop;

    @Override
    public void run() {
        for (int i = 0; i < loop; i++) {
            counter.increment();
        }
    }
}
