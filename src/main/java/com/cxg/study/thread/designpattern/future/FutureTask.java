package com.cxg.study.thread.designpattern.future;   // Administrator 于 2019/7/24 创建;

/**
 * future任务
 */
public interface FutureTask<T> {
    T call();
}
