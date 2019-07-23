package com.cxg.study.thread.designpattern.observe;   // Administrator 于 2019/7/23 创建;

import com.cxg.study.thread.designpattern.observe.base.BinaryObserver;
import com.cxg.study.thread.designpattern.observe.base.OctalObserve;
import com.cxg.study.thread.designpattern.observe.base.Subject;
import com.cxg.study.thread.designpattern.observe.thread_observe.ThreadLifeCycleObserver;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        test2();
//        test1();
    }

    private static void test2() {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("A01", "A02", "A03", "A04", "A05"));
    }

    private static void test1() {
        final Subject subject = new Subject();
        BinaryObserver binaryObserver = new BinaryObserver(subject);
        OctalObserve octalObserve = new OctalObserve(subject);

        subject.setEvent(5);
        System.out.println("==========");
        subject.setEvent(5);
        System.out.println("&&&&&&&&&&&&&&&");
        subject.setEvent(10);
    }
}
