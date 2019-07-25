package com.cxg.study.thread.volatiled;   // Administrator 于 2019/7/24 创建;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * volatile关键字：
 * 1.可见性（缓存一致性协议）：在多线程环境下，某个共享变量如果被其中一个线程给修改了，其他线程能够立即知道这个共享变量已经被修改了，
 * 当其他线程要读取这个变量的时候，最终会去内存中读取，而不是从自己的工作空间中读取；
 * 2.有序性：虚拟机在编译的时候，是有可能把代码进行重排序；volatile能避免jvm的重排序；
 * <p>
 * <p>
 * 由于Java中的运算并非是原子操作，所以导致volatile声明的变量无法保证线程安全。
 * <p>
 * 应用场景：标识位（ture/false判断）
 */
public class VolatileTest {
    private static boolean flag;
    private static volatile int number;
    private static int count;
    private final static int MAX = 10;

    public static void main(String[] args) {
        test4();
//        test3();
//        test2();
//        test1();
    }

    /**
     * volatile关键字的线程可见性测试；
     * count变量如果没用volatile关键字修饰，则【updater】线程更新了value后，【reader】无感知；
     */
    private static void test4() {
        new Thread(() -> {
            int value = count;
            while (value < MAX) {
//                添加了以下代码，即使没有volatile关键字修饰，【reader】线程仍然知道count的变化；原因不详；
//                String s = new String();

                if (value != count) {
                    System.out.println(value + "===" + count);
                    value = count;
                    System.out.printf("线程【%s】的value值更新了：【%s】\n", Thread.currentThread().getName(), value);
                }
            }
        }, "reader").start();

        new Thread(() -> {
            int value = count;
            while (count < MAX) {
                System.out.printf("线程【%s】更新了value值：【%s】\n", Thread.currentThread().getName(), ++value);
                count = value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "updater").start();
    }

    /**
     * 标识位使用场景测试
     */
    private static void test3() {
        ViewDemo viewDemo = new ViewDemo();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.printf("线程：【%s】更新了flag标识。。。\n", Thread.currentThread().getName());
                viewDemo.setFlag(true);
                viewDemo.setName("cxg");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "creater").start();

        new Thread(() -> {
            while (!viewDemo.isFlag()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("线程：【%s】循环阻塞中。。。\n", Thread.currentThread().getName());
            }
            System.out.printf("线程：【%s】跳出循环阻塞。。。【%s】\n", Thread.currentThread().getName(), viewDemo.getName());
        }, "reader").start();
    }

    /**
     * volatile修饰的共享变量，在多线程中依然是线程不安全的，这是由于cpu执行线程切换造成
     */
    private static void test2() {
        Stream.of("a01", "a02", "a03", "a04", "a05").forEach(n -> {
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

    /**
     * jvm重排序；
     * volatile可防止重排序；
     * volatile修饰的变量，变量之前的代码不会被编译在该变量之后；反之同理；
     * 如下：falg，number 2行代码的执行顺序可能会被重排序；
     * 如果falg或number变量加了volatile关键字来修饰，则2个变量不会被重排序；
     */
    private static void test1() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            new Reader("t_" + i).start();
        });
        number = 42;
        flag = true;

        /**
         * 重排序后的代码如下：
         *         flag = true;
         *         number = 42;
         *  可能导致有线程的输出结果为初始值：0
         */
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

