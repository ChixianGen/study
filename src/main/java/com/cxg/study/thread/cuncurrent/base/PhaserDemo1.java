package com.cxg.study.thread.cuncurrent.base;   // Administrator 于 2019/8/30 创建;

import com.cxg.study.utils.SleepUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * phaser.arrive() -- 当前线程到达后，不会阻塞等待其他线程的到达，而会继续往下执行；
 */
@Slf4j
public class PhaserDemo1 {
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    public static void main(String[] args) {
        Phaser phaser = new Phaser(5);
        for (int i = 0; i < 4; i++) {
            new ActionTask("t_" + i, phaser).start();
        }

        phaser.arriveAndAwaitAdvance();
        log.info("所有线程的 aaaaa 都执行完毕");
    }

    static class ActionTask extends Thread {
        private final Phaser phaser;
        ActionTask(String name, Phaser phaser) {
            super(name);
            this.phaser = phaser;
        }

        @Override
        public void run() {
            String name = getName();
            aaaaa(name);
            bbbbb(name);
        }

        private void aaaaa(String name) {
            log.debug("{} 开始执行 aaaaa 任务", name);
            SleepUtils.workingByBlock((long) RANDOM.nextInt(3000));
            log.debug("{} 结束任务 aaaaa", name);
            phaser.arrive();
        }

        private void bbbbb(String name) {
            log.debug("{} 开始执行 bbbbb 任务", name);
            SleepUtils.workingByBlock((long) RANDOM.nextInt(3000));
            log.debug("{} 结束任务 bbbbb", name);
            phaser.arrive();
        }

    }
}
