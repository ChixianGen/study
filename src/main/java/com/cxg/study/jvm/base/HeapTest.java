package com.cxg.study.jvm.base;   // Administrator 于 2019/8/12 创建;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HeapTest {
    private byte[] a = new byte[1024*100];
    public static void main(String[] args) throws InterruptedException {
        List<HeapTest> heapTests = new ArrayList<>();
        while (true) {
            heapTests.add(new HeapTest());
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}
