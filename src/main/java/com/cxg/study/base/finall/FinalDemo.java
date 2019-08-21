package com.cxg.study.base.finall;   // Administrator 于 2019/8/21 创建;

import lombok.Data;

public class FinalDemo {

    private static final String NAME = "cxg"; // 静态类变量只能在申明变量时初始化值；
    private final String NICK;
    private final Person person;

    public FinalDemo() {
        this.person = new Person();
        this.NICK = "ne";
    }
    public FinalDemo(String name, Person person) {
        this.NICK = name;
        this.person = person;
    }

    public static void main(String[] args) {

    }
}

@Data
class Person{
    private String name;
}