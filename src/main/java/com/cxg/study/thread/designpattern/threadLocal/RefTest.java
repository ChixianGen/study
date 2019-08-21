package com.cxg.study.thread.designpattern.threadLocal;   // Administrator 于 2019/8/13 创建;

public class RefTest {

    public static void main(String[] args) {
        MockThreadLocal<String> stringMockThreadLocal1 = new MockThreadLocal<String>("string_threadlocal_01");
        MockThreadLocal<Integer> stringMockThreadLocal2 = new MockThreadLocal<Integer>("string_threadlocal_02");

        MockThread mockThread1 = MockThread.newIntance(stringMockThreadLocal1, "mock_thread_" + 1);
        MockThread mockThread2 = MockThread.newIntance(stringMockThreadLocal1, "mock_thread_" + 2);

        stringMockThreadLocal1.put(mockThread1, "hello_thread_01");
        stringMockThreadLocal1.put(mockThread2, "hello_thread_02");

        stringMockThreadLocal2.put(mockThread1,1314);

        System.out.println(stringMockThreadLocal2.get(mockThread1));

        System.out.println(stringMockThreadLocal1.get(mockThread1));
        System.out.println(stringMockThreadLocal1.get(mockThread2));
    }
}
