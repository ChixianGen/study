package com.cxg.study.thread.singleton;   // Administrator 于 2019/7/23 创建;

public class Singleton2 {

    public static Singleton2 getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static Singleton2 INSTANCE = new Singleton2();
    }

}
