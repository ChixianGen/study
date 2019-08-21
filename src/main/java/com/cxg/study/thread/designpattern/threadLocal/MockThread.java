package com.cxg.study.thread.designpattern.threadLocal;   // Administrator 于 2019/8/15 创建;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 模拟 java.lang.Thread 对象
 */
@Data
@RequiredArgsConstructor
public class MockThread {
    @NonNull
    private String name;
    @NonNull
    private MockThreadLocal mockThreadLocal;

    private MockThreadLocal.MockThreadLocalMap threadLocalMap;

//    private static MockThread self;

    public MockThread(MockThreadLocal mockThreadLocal, String name) {
        this.name = name;
        this.mockThreadLocal = mockThreadLocal;
        threadLocalMap = mockThreadLocal.newMockThreadLocalMap();
    }

    public static MockThread newIntance(MockThreadLocal mockThreadLocal, String name) {
        MockThread mockThread = new MockThread(mockThreadLocal, name);
//        self = mockThread;
        return mockThread;
    }

//    public static MockThread current() {
//        return self;
//    }

    @Override
    public String toString() {
        return "MockThread{" +
                "name='" + name + '\'' +
                ", mockThreadLocal=" + mockThreadLocal +
                ", threadLocalMap=" + threadLocalMap +
                '}';
    }

}
