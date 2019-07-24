package com.cxg.study.thread.designpattern.future;   // Administrator 于 2019/7/24 创建;

/**
 * 异步future
 * @param <T>
 */
public class AsyncFuture<T> implements Future<T> {

    private volatile boolean finish;

    private T result;

    @Override
    public T get() throws InterruptedException {
        synchronized (this) {
            while (!finish) {
                this.wait();
            }
        }
        return result;
    }

    public void handleResult(T result) {
        synchronized (this) {
            this.finish = true;
            this.result = result;
            this.notifyAll();
        }
    }
}
