package com.cxg.study.thread.designpattern.threadLocal;   // Administrator 于 2019/8/13 创建;

public class RefTest {

    public static void main(String[] args) {
        MockThreadLocal<String> stringMockThreadLocal = new MockThreadLocal<String>("mock");
        MockThread mockThread = new MockThread(stringMockThreadLocal);
    }
}
