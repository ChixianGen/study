package com.cxg.study.thread.base;   // 16612 于 2019/8/8 创建;

import lombok.Data;

/**
 * @ClassName: Test
 * @author: 16612
 * @date: 2019/8/8 23:21
 * @Copyright: All rights reserved.
 */
public class Test {
    public static void main(String args[]) {
//        test1();
//        test2();
//        test3();
    }

    // 自定义线程中断标记
    private static void test3() {
        C c = new C();
        Thread thread = new Thread(c);
        thread.start();
        for (int i = 0; i < 10; i++) {
            System.out.printf("线程：【%s】--【%s】\n", Thread.currentThread().getName(), i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 3) {
                c.setFlag(false);
            }
        }
    }

    private static void test2() {
        Thread thread = new Thread(new B());
        thread.start();

        for (int i = 0; i < 10; i++) {
            System.out.printf("线程：【%s】--【%s】\n", Thread.currentThread().getName(), i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 3) {
                thread.interrupt(); // 仅仅是设置中断标识；
            }
        }
    }

    private static void test1() {
        new A().run(); // 普通的方法调用，并没有启动一个新的线程；
        new A().start(); // 启动一个新的线程；
    }

}

@Data
class C implements Runnable {
    private boolean flag = true;
    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.printf("线程：【%s】--【%s】\n", Thread.currentThread().getName(), i++);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}


class B implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            // interrupted 方法会清除线程的中断标识；
            if (Thread.interrupted()) {
                break;
            }
            System.out.printf("线程：【%s】--【%s】\n", Thread.currentThread().getName(), i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("===============外部标记当前线程为【中断状态】===============");

                // 抛出异常后会清除当前线程的中断标识，所以需要重新标记中断标识；
                Thread.currentThread().interrupt();
            }
        }
    }
}

class A extends Thread {
    @Override
    public void run() {
        System.out.printf("当前线程：%s\n", currentThread().getName());
    }
}
