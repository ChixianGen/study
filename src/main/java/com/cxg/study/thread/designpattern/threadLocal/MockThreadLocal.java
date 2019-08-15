package com.cxg.study.thread.designpattern.threadLocal;   // Administrator 于 2019/8/15 创建;

import lombok.Data;

/**
 * 模拟 java.lang.ThreadLocal 对象
 */
@Data
public class MockThreadLocal<T> {
    String name;
    MockThread mockThread;

    public MockThreadLocal(String name) {
        this.name = name;
    }

//    public T get() {
//        MockThreadLocalMap map = mockThread.getThreadLocalMap();
//
//        return T;
//    }

//    public MockThreadLocalMap newMockThreadLocalMap() {
//        return new MockThreadLocalMap();
//    }

//    static class MockThreadLocalMap<T> {
//        Map map = new HashMap<>();
//
//        public T get() {
//            return map.get();
//        }
//
//        public void put(MockThreadLocal<T> key) {
//
//        }
//    }
}
