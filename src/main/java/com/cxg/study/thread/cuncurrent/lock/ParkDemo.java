package com.cxg.study.thread.cuncurrent.lock;   // Administrator 于 2019/8/14 创建;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ParkDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread main = Thread.currentThread();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.unpark(main);
            System.out.printf("线程【%s】unpark主线程\n", Thread.currentThread().getName());
        }, "other").start();

        LockSupport.park();
        System.out.println("main");
    }
}