package com.cxg.study.base.finall;   // Administrator 于 2019/8/21 创建;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
        Person person = new Person("aaa");
        todo(person);
        System.out.println(person);
    }

    private static void todo(final Person ps) {
        ps.setName("cxg");

// final参数不能修改参数值；这里对象参数ps的参数值是调用方（这里是main函数）person对象的堆内存地址的拷贝，也就是内存地址；
//        person = new Person("smg");
    }
}

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
class Person{
    private String name;
}