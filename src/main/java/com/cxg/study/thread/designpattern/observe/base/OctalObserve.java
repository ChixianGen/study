package com.cxg.study.thread.designpattern.observe.base;   // Administrator 于 2019/7/23 创建;

import java.util.Optional;

/**
 * 八进制观察者
 */
public class OctalObserve extends Observer{

    public OctalObserve(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        Optional.of("OctalObserve：【" + Integer.toOctalString(subject.getEvent()) + "】")
                .ifPresent(System.out::println);
    }
}
