package com.cxg.study.thread.cuncurrent.base;   // Administrator 于 2019/8/30 创建;

import com.cxg.study.utils.SleepUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * 可代替 {@link java.util.concurrent.CountDownLatch} 和 {@link java.util.concurrent.CyclicBarrier}来使用
 * {@link Phaser}
 * phaser.arriveAndAwaitAdvance() -- 阻塞等待多个线程同时到达后才会继续往下执行，类似于 CountDownLatch 的 await() + countDown()
 * phaser.arriveAndDeregister() -- 根据实际场景使用；（可注销异常或长时间阻塞的线程，动态的控制任务执行的线程数量；）
 */
@Slf4j
public class PhaserDemo {

    private static final Integer NUM = 5;
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
//        arriveAndAwaitAdvance();

        final Phaser phaser = new Phaser();
        arriveAndDeregister(phaser);
    }

    /**
     * 模拟多个线程同时执行某一任务，而其中有线程在执行某一步骤时异常退出的情况；
     */
    private static void arriveAndDeregister(Phaser phaser) {
        phaser.bulkRegister(NUM + 1);
        for (int i = 0; i < NUM; i++) {
            new NormalTask(phaser, "t_" + i).start();
        }

        new UnNormalTask(phaser, "t_e").start();
    }

    /**
     * 模拟多个线程同时到达某一时刻后再继续往下执行；
     */
    private static void arriveAndAwaitAdvance() {
        final Phaser phaser = new Phaser(NUM);
        for (int i = 0; i < NUM; i++) {
            new NormalTask(phaser, "t_" + i).start();
        }
    }

    static class UnNormalTask extends Thread {
        final Phaser phaser;

        UnNormalTask(Phaser phaser, String name) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            String name = getName();
            aaaaa(name);
            bbbbb(name);
            try {
                ccccc(name);
            } catch (RuntimeException e) {
                log.debug("{} 捕获异常：{}", name, e.toString());

                /**
                 * 这个方法表示当前线程到达了这个状态，但由于发生异常，注销了当前线程；
                 * 所以其他线程不会阻塞；
                 */
                phaser.arriveAndDeregister();
                log.debug("{} 异常注销后 phaser 剩余的注册parties数量：{}", name, phaser.getRegisteredParties());
            }
        }

        private void aaaaa(String name) {
            log.debug("{} 执行 aaaaa 方法", name);
            SleepUtils.workingByBlock((long) RANDOM.nextInt(3000));
            int i1 = phaser.arriveAndAwaitAdvance();
            log.debug("{} 的到达值 {} {}", name, i1, phaser.getPhase());
            log.debug("{} phaser 剩余的注册parties数量：{}", name, phaser.getRegisteredParties());
        }

        private void bbbbb(String name) {
            log.debug("{} 执行 bbbbb 方法", name);
            SleepUtils.workingByBlock((long) RANDOM.nextInt(3000));
            int i1 = phaser.arriveAndAwaitAdvance();
            log.debug("{} 的到达值 {} {}", name, i1, phaser.getPhase());
            log.debug("{} phaser 剩余的注册parties数量：{}", name, phaser.getRegisteredParties());
        }

        private void ccccc(String name) throws RuntimeException {
            SleepUtils.workingByBlock((long) RANDOM.nextInt(3000));
            /**
             * 模拟发生异常，并没有执行 phaser.arriveAndAwaitAdvance() 方法；
             * 其他线程可能会因为当前线程未到达而阻塞等待，所以需要特殊处理；
             * 处理方法就是调用 phaser.arriveAndDeregister() 注销当前线程的方法；（上面的try-catch处理）
             */
            throw new RuntimeException("发生异常退出");
        }
    }

    static class NormalTask extends Thread {
        final Phaser phaser;

        NormalTask(Phaser phaser, String name) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            String name = getName();
            aaaaa(name);
            bbbbb(name);
            ccccc(name);
        }

        private void aaaaa(String name) {
            log.debug("{} 执行 aaaaa 方法", name);
            SleepUtils.workingByBlock((long) RANDOM.nextInt(3000));
            int i1 = phaser.arriveAndAwaitAdvance();
            log.debug("{} 的到达值 {} {}", name, i1, phaser.getPhase());
            log.debug("{} phaser 剩余的注册parties数量：{}", name, phaser.getRegisteredParties());
        }

        private void bbbbb(String name) {
            log.debug("{} 执行 bbbbb 方法", name);
            SleepUtils.workingByBlock((long) RANDOM.nextInt(3000));
            int i1 = phaser.arriveAndAwaitAdvance();
            log.debug("{} 的到达值 {} {}", name, i1, phaser.getPhase());
            log.debug("{} phaser 剩余的注册parties数量：{}", name, phaser.getRegisteredParties());
        }

        private void ccccc(String name) {
            log.debug("{} 执行 ccccc 方法", name);
            SleepUtils.workingByBlock((long) RANDOM.nextInt(3000));
            int i1 = phaser.arriveAndAwaitAdvance();
            log.debug("{} 的到达值 {} {}", name, i1, phaser.getPhase());
            log.debug("{} phaser 剩余的注册parties数量：{}", name, phaser.getRegisteredParties());
        }
    }
}
