package com.cxg.study.thread.designpattern.future;   // Administrator 于 2019/7/24 创建;

import java.util.function.Consumer;

public class FutureService {

    // future轮询方式处理结果；
    // com.cxg.study.thread.designpattern.future.AsyncFuture.get
    public <T> Future<T> submit(final FutureTask<T> task) {
        AsyncFuture<T> asyncFuture = new AsyncFuture();
        new Thread(() -> {
            T result = task.call();
            asyncFuture.handleResult(result);
        }).start();
        return asyncFuture;
    }

    // callback 回调通知方式处理结果；(推荐使用，无需Future设计模式)
    public <T> void submit(final FutureTask<T> task, final Consumer<T> consumer) {
//        AsyncFuture<T> asyncFuture = new AsyncFuture();
        new Thread(() -> {
            T result = task.call();
//            asyncFuture.handleResult(result);
            consumer.accept(result);
        }).start();
    }
}
