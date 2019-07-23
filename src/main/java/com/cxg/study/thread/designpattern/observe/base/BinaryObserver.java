package com.cxg.study.thread.designpattern.observe.base;   // Administrator 于 2019/7/23 创建;

import java.util.Optional;

/**
 * 二进制观察者
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        Optional.of("BinaryObserver：【" + Integer.toBinaryString(subject.getEvent()) + "】")
                .ifPresent(System.out::println);
    }
}
