package com.cxg.study.thread.designpattern.observe.base;   // Administrator 于 2019/7/23 创建;

// 观察者
public abstract class Observer {

    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public abstract void update();
}
