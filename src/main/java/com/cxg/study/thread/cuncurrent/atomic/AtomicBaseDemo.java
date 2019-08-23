package com.cxg.study.thread.cuncurrent.atomic;   // Administrator 于 2019/8/21 创建;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.IntStream;

@Slf4j
public class AtomicBaseDemo {

    @Test
    public void atomicReferenceFieldUpdater() {

    }

    @Test
    public void atomicIntegerArray() {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(3);
        atomicIntegerArray.set(0, 5);
        System.out.println("atomicIntegerArray.get(0) = " + atomicIntegerArray.get(0));
    }

    @Test
    public void atomicStampedReference() throws InterruptedException {
        Simple simple = new Simple("origin");
        AtomicStampedReference<Simple> reference = new AtomicStampedReference(simple, 0);
        System.out.println(reference.getStamp());
        System.out.println(reference.getReference());
        IntStream.rangeClosed(1, 9).forEach(i -> {
            new Thread(() -> {
                Simple simple1 = new Simple("t_" + i);
                boolean flag = false;
                while (!flag) {
                    String name = Thread.currentThread().getName();
                    Simple r = reference.getReference();
                    int stamp = reference.getStamp();
                    log.debug("线程：{}的操作前的ref对象：{}，版本号：{}", name, r, stamp);
                    flag = reference.compareAndSet(r, simple1, stamp, ++stamp);
                    log.debug("线程：{}的操作后的ref对象：{}，版本号：{}，结果：{}", name, reference.getReference(), reference.getStamp(), flag);
                    if (!flag) {
                        try {
                            // 休眠为了让线程执行权流转，而不是某线程长期霸占CPU执行权资源；
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                log.info("=========================线程：{}操作成功，版本号：{}，simple对象：{}", Thread.currentThread().getName(), reference.getStamp(), reference.getReference());
            }, "thread_" + i).start();
        });

        TimeUnit.SECONDS.sleep(1);
        log.debug("最终的版本号：{}", reference.getStamp());
        log.debug("最终simple对象：{}", reference.getReference());
    }

    @Test
    public void atomicReference() {
        AtomicReference<Simple> reference = new AtomicReference<>(new Simple("cxg"));
        Simple simple = reference.get();
        System.out.println(simple);
    }
}

@ToString
@AllArgsConstructor
class Simple {
    String name;
}
