package com.cxg.study.thread.cuncurrent.lock;   // Administrator 于 2019/8/26 创建;

import com.cxg.study.utils.SleepUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * write write -> NO
 * write read  -> NO
 * read  write -> NO
 * read  read  -> YES
 */
@Slf4j
public class ReadWriteLockDemo {

    private final static ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private final static Lock READ_LOCK = READ_WRITE_LOCK.readLock();
    private final static Lock WRITE_LOCK = READ_WRITE_LOCK.writeLock();
    private final static List<Long> TIMES = Lists.newArrayListWithCapacity(10);

    public static void main(String[] args) {
//        writeAndRead();
//        writeAndWrite();
//        readAndRead();
    }

    private static void readAndRead() {
        for (int i = 0; i < 10; i++) {
            long e = System.currentTimeMillis();
            TIMES.add(e);
            SleepUtils.workingByBlock(8);
        }

        Thread readThread1 = new Thread(ReadWriteLockDemo::read, "read_thread_111111111");
        Thread readThread2 = new Thread(ReadWriteLockDemo::read, "read_thread_222222222");
        Thread readThread3 = new Thread(ReadWriteLockDemo::read, "read_thread_333333333");
        readThread1.start();
        readThread2.start();
        readThread3.start();
    }

    private static void writeAndWrite() {
        Thread writeThread1 = new Thread(ReadWriteLockDemo::write, "write_thread_01");
        Thread writeThread2 = new Thread(ReadWriteLockDemo::write, "write_thread_02");
        writeThread1.start();
        writeThread2.start();
    }

    /**
     * 写锁线程占有资源，知道资源释放，读锁线程才能继续操作；
     */
    private static void writeAndRead() {
        Thread writeThread = new Thread(ReadWriteLockDemo::write, "write_thread");
        Thread readThread = new Thread(ReadWriteLockDemo::read, "_read_thread");
        writeThread.start();

        SleepUtils.workingByBlock(10);
        readThread.start();
    }

    private static void write() {
        String name = Thread.currentThread().getName();
        try {
            log.debug("{} into write method...", name);
            WRITE_LOCK.lock();
            log.debug("{} get the write lock...", name);
            for (int i = 0; i < 5; i++) {
                long e = System.currentTimeMillis();
                TIMES.add(e);
                log.debug("{} working add {}", name, e);
                SleepUtils.workingByBlock(1000);
            }
        } finally {
            log.debug("{} release the write lock...", name);
            WRITE_LOCK.unlock();
        }
    }

    private static void read() {
        String name = Thread.currentThread().getName();
        try {
            log.debug("{} into read method...", name);
            READ_LOCK.lock();
            log.debug("{} get the read lock...", name);
            TIMES.forEach(lg -> {
                log.info("{} read the value: {}", name, lg);
                SleepUtils.workingByBlock(100);
            });
        } finally {
            log.debug("{} release the read lock...", name);
            READ_LOCK.unlock();
        }
    }
}
