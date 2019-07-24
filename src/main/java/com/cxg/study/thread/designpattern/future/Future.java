package com.cxg.study.thread.designpattern.future;   // Administrator 于 2019/7/24 创建;

public interface Future<T> {
    T get() throws InterruptedException;
}
