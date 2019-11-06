package com.cxg.study.base.anno;

/**
 * @Author: cxg
 * @Date: 2019/10/27 17:07
 * @Copyright: All rights reserved.
 */
public class TreeAnnoDemo {

    @Tree
    public void demo1(String value) {
        System.out.println("demo1 ===> value: " + value);
    }

    @Tree(value = "hello")
    public void demo2(String value) {
        System.out.println("demo2 ===> value: " + value);
    }

    public void demo3(String value) {
        System.out.println("demo3 ===> value: " + value);
    }

    public static void main(String args[]) throws Exception {
        TreeProcessor p = new TreeProcessor<>();
        p.parseMethod(TreeAnnoDemo.class);
    }
}
