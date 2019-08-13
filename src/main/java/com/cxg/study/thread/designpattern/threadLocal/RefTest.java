package com.cxg.study.thread.designpattern.threadLocal;   // Administrator 于 2019/8/13 创建;

import com.cxg.study.socket.base.udp.Person;

public class RefTest {
    public static void main(String[] args) {
        Person a = new Person(1, "cxg", 88888);
        Person b = a;
        System.out.printf("a->[%s], hashcode->[%s]\n", a, a.hashCode());
        System.out.printf("b->[%s], hashcode->[%s]\n", b, b.hashCode());
    }
}
