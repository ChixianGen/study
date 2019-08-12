package com.cxg.study.jvm.base;   // Administrator 于 2019/8/12 创建;

public class Test {
    public static final Integer CONSTANT = 666;

    public static void main(String[] args) {
        Test test = new Test();
        int num = test.compute();
        System.out.println(num);
    }

    public int compute() {
        int a = 5;
        int b = 8;
        int c = (a + b) * 2;
        return c;
    }
}
