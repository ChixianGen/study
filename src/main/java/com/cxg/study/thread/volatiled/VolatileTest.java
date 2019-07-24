package com.cxg.study.thread.volatiled;   // Administrator 于 2019/7/24 创建;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * volatile关键字：
 * 1.可见性（缓存一致性协议）：在多线程环境下，某个共享变量如果被其中一个线程给修改了，其他线程能够立即知道这个共享变量已经被修改了，
 * 当其他线程要读取这个变量的时候，最终会去内存中读取，而不是从自己的工作空间中读取；
 * 2.有序性：虚拟机在编译的时候，是有可能把代码进行重排序；volatile能避免jvm的重排序；
 *
 *
 * 由于Java中的运算并非是原子操作，所以导致volatile声明的变量无法保证线程安全。
 */
public class VolatileTest {
    private static boolean flag;
    private static int number;

    public static void main(String[] args) {
        test2();
//        test1();
    }

    private static void test2() {
        Stream.of("a01","a02", "a03", "a04", "a05").forEach(n -> {
            new Thread(() -> {
                for (int i = 0; i < 3; i++) {
//                    synchronized (VolatileTest.class) {
                        number = number + 1;
                        System.out.printf("线程：【%s】的number值：【%s】\n", Thread.currentThread().getName(), number);
//                    }
                }
            }, n).start();
        });
    }

    private static void test1() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            new Reader("t_" + i).start();
        });
        number = 42;
        flag = true;
    }

    private static class Reader extends Thread {
        public Reader(String name) {
            super(name);
        }

        @Override
        public void run() {
            while (!flag) {
            }
            System.out.printf("线程：【%s】，number:【%s】\n", currentThread().getName(), number);
        }
    }
}

