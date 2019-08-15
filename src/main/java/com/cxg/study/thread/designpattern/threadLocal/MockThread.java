package com.cxg.study.thread.designpattern.threadLocal;   // Administrator 于 2019/8/15 创建;

import lombok.Data;

/**
 * 模拟 java.lang.Thread 对象
 */
@Data
public class MockThread {

    private MockThreadLocal mockThreadLocal;

//    private MockThreadLocal.MockThreadLocalMap threadLocalMap;

    public MockThread(MockThreadLocal mockThreadLocal) {
        this.mockThreadLocal = mockThreadLocal;
//        threadLocalMap = mockThreadLocal.newMockThreadLocalMap();
    }
}
