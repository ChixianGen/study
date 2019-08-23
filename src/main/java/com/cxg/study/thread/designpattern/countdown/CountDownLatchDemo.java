package com.cxg.study.thread.designpattern.countdown;   // Administrator 于 2019/8/23 创建;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class CountDownLatchDemo {

    private List<String> stringList;

    /**
     * 调用latch.countDown()的线程执行完成后，调用latch.await()的线程才继续执行；
     */
    @Test
    public void test1() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Thread thread1 = new Thread(() -> {
            try {
                log.debug("线程：{}开始执行任务", Thread.currentThread().getName());
                stringList = Lists.newArrayList("hello", "world", "friday");
                log.debug("线程：{}任务完成", Thread.currentThread().getName());
            } finally {
                latch.countDown();
            }
        }, "thread_1");

        Thread thread2 = new Thread(() -> {
            try {
                log.debug("线程：{}等待执行任务", Thread.currentThread().getName());
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("线程：{}开始执行任务", Thread.currentThread().getName());
            stringList.forEach(s -> {
                log.info(s);
            });
            log.debug("线程：{}任务完成", Thread.currentThread().getName());
        }, "thread_2");

        Thread thread3 = new Thread(() -> {
            try {
                log.debug("线程：{}等待执行任务", Thread.currentThread().getName());
                latch.await();
                log.debug("线程：{}任务完成", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread_3");

        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
    }
}
