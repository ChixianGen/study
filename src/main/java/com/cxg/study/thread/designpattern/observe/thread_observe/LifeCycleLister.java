package com.cxg.study.thread.designpattern.observe.thread_observe;   // Administrator 于 2019/7/23 创建;

// 观察者
public interface LifeCycleLister {

    void onEvent(ObserverRunnable.RunnableEvent event);
}
