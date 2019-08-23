package com.cxg.study.base.paramtransfer;   // Administrator 于 2019/8/21 创建;

import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 参数传递示例
 */
public class ParamTransferDemo {
    public static void main(String[] args) {
//        baseDataType();
        refDataType();
//        arrayDataTest();
    }

    private static void arrayDataTest() {
        String[] strings = {"a", "b"};
        System.out.printf("add()方法调用前的对象：【%s】，hashcode：【%s】\n", strings, strings.hashCode());
        add(strings);
        System.out.printf("add()方法调用后的对象：【%s】，hashcode：【%s】\n", strings, strings.hashCode());
    }

    private static void add(String[] strings) {
        strings[0] = "c";
        System.out.printf("add()方法内部的对象：【%s】，hashcode：【%s】\n", strings, strings.hashCode());
    }

    /**
     * 执行todo()方法时，传递的是obj对象在栈中的引用值；是值传递；
     */
    private static void refDataType() {
        Obj obj = new Obj("A");
        System.out.printf("todo()方法调用前的对象：【%s】，hashcode：【%s】\n", obj, obj.hashCode());
        // 调用todo()方法时，复制一份obj变量指向堆内存对象的内存地址值，传入给方法的current变量，属于值传递；
        todo(obj);
        System.out.printf("todo()方法调用后的对象：【%s】，hashcode：【%s】\n", obj, obj.hashCode());
    }

    private static void todo(Obj current) {
        current = new Obj("B"); // current变量指向了一个新的Obj对象；
//        current.name = "hello"; // current变量还是指向调用方的堆内存对象，所以会对调用方的obj对象做修改；
        System.out.printf("todo()方法内部的对象：【%s】，hashcode：【%s】\n", current, current.hashCode());
    }

    // 传递的是存储单元中的内容，而不是存储单元的引用
    private static void baseDataType() {
        int num = 1;
        System.out.println("changeNum()方法调用之前：num = " + num);
        changeNum(num);
        System.out.println("changeNum()方法调用之后：num = " + num);
    }

    private static void changeNum(int num) {
        num = 8;
    }
}

@ToString
@AllArgsConstructor
class Obj{
    String name;
}