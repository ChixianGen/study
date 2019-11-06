package com.cxg.study.thread.cuncurrent.future;   // 16612 于 2019/9/18 创建;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @ClassName: Demo
 * @Description: TODO
 * @author: 16612
 * @date: 2019/9/18 23:14
 * @Copyright: All rights reserved.
 */
public class Demo {
    public static void main(String args[]) {
//        Arrays.asList("hello", "world", "cxg").stream().forEach(s -> test(s, p -> p.length()));
        test1(s -> s.length() + 1);
    }

    public static void test(String string, Apple apple) {
        System.out.println(apple.num(string));
    }

    public static void test1(Apple apple) {
        String string = "hello world";
        System.out.println(apple.num(string));
    }

    interface Apple {
        int num(String string);
    }
}
