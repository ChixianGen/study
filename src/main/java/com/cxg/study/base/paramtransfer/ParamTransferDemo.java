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
    }

    private static void refDataType() {
        Obj obj = new Obj("A");
        System.out.printf("todo()方法调用前的对象：【%s】，hashcode：【%s】\n", obj, obj.hashCode());
        todo(obj);
        System.out.printf("todo()方法调用后的对象：【%s】，hashcode：【%s】\n", obj, obj.hashCode());
    }

    private static void todo(Obj obj) {
        obj = new Obj("B");
        System.out.printf("todo()方法内部的对象：【%s】，hashcode：【%s】\n", obj, obj.hashCode());
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