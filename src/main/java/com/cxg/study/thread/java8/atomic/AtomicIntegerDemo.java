package com.cxg.study.thread.java8.atomic;   // 16612 于 2019/8/13 创建;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: AtomicIntegerDemo
 * @author: 16612
 * @date: 2019/8/13 21:45
 * @Copyright: All rights reserved.
 */
public class AtomicIntegerDemo {
    public static void main(String args[]) {
        test1();
    }

    private static void test1() {
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger.getAndSet(2));
        System.out.println(atomicInteger.get());
        atomicInteger.lazySet(45);
        System.out.println(atomicInteger.incrementAndGet());
        /*for (int i = 0; i < 10; i++) {
            System.out.println(atomicInteger.getAndIncrement());
        }*/
    }
}
