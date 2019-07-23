package com.cxg.study.thread.designpattern.observe.base;   // Administrator 于 2019/7/23 创建;

import java.util.ArrayList;
import java.util.List;

// 主题
public class Subject {

    private int event; // 模拟被观察的事件源；

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        if (event == this.event) {
            return;
        }
        this.event = event;
        notifyObservers();
    }

    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        observers.stream().forEach(Observer::update);
    }
}
