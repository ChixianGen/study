package com.cxg.study.thread.designpattern.countdown;   // Administrator 于 2019/7/25 创建;

public class CountDownLaunchDemo {

    private final int total;

    private int counter;

    public CountDownLaunchDemo(int total) {
        this.total = total;
    }

    public void countDown() {
        synchronized (this) {
            System.out.printf("进入countDown同步块的线程：【%s】。\n", Thread.currentThread().getName());
            this.counter++;
            this.notifyAll();
        }
    }

    public void await() throws InterruptedException {
        synchronized (this) {
            while (counter < total) {
                System.out.printf("进入await同步块的线程：【%s】。\n", Thread.currentThread().getName());
                this.wait();
            }
        }
    }
}
