package com.cxg.study.thread.cuncurrent.base;   // Administrator 于 2019/8/30 创建;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 所有人每隔5秒钟抽一次签，每个人有50%的概率留下来或被淘汰。
 * 留下来的人下次抽签时同样有50%的概率被淘汰。被淘汰的人下次抽签时同样有50%的概率复活。
 * 团队所有成员都被淘汰完，为挑战失败，团队所有成员都回到游戏中（除刚开始外），为挑战成功。
 * 比如一开始10人参与游戏，第一轮抽签后，6人留下，4人淘汰。
 * 第二轮抽签后，留下的6人中4人被淘汰，淘汰的4人中2人复活，那么目前是4人在游戏中，6人被淘汰。
 * 一直如此继续下去，直到10人全部被淘汰，或全部回到游戏中。
 */
@Slf4j
public class PhaserDemo2 {
    static final int COUNT = 6;

    public static void main(String[] args) throws Exception {
        new Thread(new Challenger("张三")).start();
        new Thread(new Challenger("李四")).start();
        new Thread(new Challenger("王五")).start();
        new Thread(new Challenger("赵六")).start();
        new Thread(new Challenger("大胖")).start();
        new Thread(new Challenger("小白")).start();
        synchronized (PhaserDemo2.class) {
            PhaserDemo2.class.wait();
        }
    }

    static Phaser ph = new Phaser() {
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            log.info("第( {} )局，剩余[ {} ]人", phase, registeredParties);
            return registeredParties == 0 ||
                    (phase != 0 && registeredParties == COUNT);
        }
    };

    static class Challenger implements Runnable {

        String name;
        int state;

        Challenger(String name) {
            this.name = name;
            this.state = 0;
        }

        @SneakyThrows
        @Override
        public void run() {
            log.debug("[ {} ] 开始挑战。。。", name);
            ph.register();
            int phase = 0;
            int h;
            while (!ph.isTerminated() && phase < 10) {
                TimeUnit.SECONDS.sleep(3);
                if (state == 0) {
                    if (Decide.goon()) {
                        h = ph.arriveAndAwaitAdvance();
                        if (h < 0) {
                            log.debug("No{}.[ {} ] 继续，但已胜利。。。", phase, name);
                        } else {
                            log.debug("No{}.[ {} ] 继续 at ( {} )。。。", phase, name, h);
                        }
                    } else {
                        state = -1;
                        h = ph.arriveAndDeregister();
                        log.debug("No{}.[ {} ] 退出 at ( {} )。。。", phase, name, h);
                    }
                } else {
                    if (Decide.revive()) {
                        state = 0;
                        h = ph.register();
                        if (h < 0) {
                            log.debug("No{}.[ {} ] 复活，但已失败。。。", phase, name);
                        } else {
                            log.debug("No{}.[ {} ] 复活at ( {} )。。。", phase, name, h);
                        }
                    } else {
                        log.debug("No{}.[ {} ] 没有复活。。。", phase, name);
                    }
                }
                phase++;
            }
            if (state == 0) {
                ph.arriveAndDeregister();
            }
            log.debug("[ {} ] 结束。。。", name);
        }

    }

    static class Decide {
        private static Random random = new Random(System.currentTimeMillis());

        static boolean goon() {
            return random.nextInt(9) > 4;
        }

        static boolean revive() {
            return random.nextInt(9) < 5;
        }
    }
}
