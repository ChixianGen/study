package com.cxg.study.thread.designpattern.threadLocal;   // Administrator 于 2019/7/24 创建;

import java.util.stream.Stream;

/**
 * ThreadLocal：始终以当前线程（Thread.currentThread()）作为key;
 *      ThreadLocal.ThreadLocalMap
 *
 *   经典用法：上下文切换时可保证数据在一个线程中的安全；（数据线程独享，非共享变量）
 */
public class SimpleThreadLocalDemo {
//    static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();
    static final InheritableThreadLocal<String> THREAD_LOCAL = new InheritableThreadLocal();
    static final InheritableThreadLocal<String> THREAD_LOCAL_1 = new InheritableThreadLocal();

    public static void main(String[] args) throws InterruptedException {
        test1();
//        test2();
    }

    private static void test2() throws InterruptedException {
        Stream.of("t_1", "t_2").forEach(n -> {
            new Thread(() -> {
                THREAD_LOCAL.set(Thread.currentThread().getName() + "_value");
                System.out.printf("线程：【%s】\n", Thread.currentThread().getName());
                new Thread(() -> {
                    System.out.printf("子线程【%s】从父线程【%s】的值：%s\n", Thread.currentThread().getName(),n, THREAD_LOCAL.get());
                }, n + "_child").start();
            }, n).start();
        });
        Thread.sleep(2000);
        System.out.println(THREAD_LOCAL.get());
    }

    private static void test1() throws InterruptedException {
        THREAD_LOCAL.set("hello");
        THREAD_LOCAL_1.set("world");
        Thread.sleep(2000);
        System.out.println(THREAD_LOCAL_1.get());
        System.out.println(THREAD_LOCAL.get());
    }
}
