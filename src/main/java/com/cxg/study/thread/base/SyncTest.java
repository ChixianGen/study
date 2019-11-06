package com.cxg.study.thread.base;

/**
 * @Author: cxg
 * @Date: 2019/10/13 14:53
 * @Copyright: All rights reserved.
 */
public class SyncTest implements Runnable {
    private int ticket = 10;

    public static void main(String args[]) {
        SyncTest syncTest = new SyncTest();
        for (int i = 0; i < 5; i++) {
            new Thread(syncTest, "t_" + i).start();
        }
    }

    @Override
    public void run() {
        while (true) {
            shellTicket();
        }
    }

    private synchronized void shellTicket() {
        if (ticket >= 1) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-->" + ticket);
            ticket--;
        }
    }
}
