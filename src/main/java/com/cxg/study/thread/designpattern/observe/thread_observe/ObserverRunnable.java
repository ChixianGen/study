package com.cxg.study.thread.designpattern.observe.thread_observe;   // Administrator 于 2019/7/23 创建;

import lombok.Data;

// 主题，被观察的对象
public abstract class ObserverRunnable implements Runnable {

    // 自定义的线程生命周期观察者；
    protected final LifeCycleLister lifeCycleLister;

    protected ObserverRunnable(final LifeCycleLister lifeCycleLister) {
        this.lifeCycleLister = lifeCycleLister;
    }

    public void notifyChange(final RunnableEvent event) {
        lifeCycleLister.onEvent(event);
    }

    public enum RunnableState {
        RUNNING,  ERROR, DONE
    }

    @Data
    public static class RunnableEvent {
        private final RunnableState runnableState;
        private final Thread thread;
        private final Throwable cause;

        public RunnableEvent(RunnableState runnableState, Thread thread, Throwable cause) {
            this.runnableState = runnableState;
            this.thread = thread;
            this.cause = cause;
        }
    }
}
