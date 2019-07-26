package com.cxg.study.classloader;   // Administrator 于 2019/7/26 创建;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 类的初始化由jvm保证线程安全；
 */
public class ClinitThreadDemo {
    public static void main(String[] args) {

        new Thread(() -> new ClinitObject(), "T_1").start();
        new Thread(() -> new ClinitObject(), "T_2").start();
    }

    /**
     * 以下例子可能不是很准确；因为static代码块始终只会执行一次；
     */
    static class ClinitObject {
        private static AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        static {
            System.out.printf("线程【%s】进入static块。。。\n", Thread.currentThread().getName());
            while (atomicBoolean.get()) {
                try {
                    Thread.sleep(2000);
                    atomicBoolean.set(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("线程【%s】退出static块。。。\n", Thread.currentThread().getName());
        }
    }
}
