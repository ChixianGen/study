package com.cxg.study.thread.cuncurrent.base;   // Administrator 于 2019/8/26 创建;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Exchanger;

/**
 * {@link Exchanger}
 */
@Slf4j
public class ExchangeDemo {

    public static void main(String[] args) {
        test1();
    }

//    @Test
    public static void test1() {
        Exchanger<Object> exchanger = new Exchanger<>();
        new Thread(() -> {
            Object obj = new Object();
            log.debug("线程：{}中的原始对象：{}", Thread.currentThread().getName(), obj);
            try {
                Object result = exchanger.exchange(obj);
                log.info("线程：{}中的返回对象：{}", Thread.currentThread().getName(), result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "===OrderDTO===").start();

        new Thread(() -> {
            Object obj = new Object();
            log.debug("线程：{}中的原始对象：{}", Thread.currentThread().getName(), obj);
            try {
                Object result = exchanger.exchange(obj);
                log.info("线程：{}中的返回对象：{}", Thread.currentThread().getName(), result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "===B===").start();
    }
}
