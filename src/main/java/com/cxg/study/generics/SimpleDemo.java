package com.cxg.study.generics;   // Administrator 于 2019/8/20 创建;

import java.util.ArrayList;
import java.util.List;

public class SimpleDemo {
    public static void main(String[] args) {
        List<? extends Number> list = new ArrayList<Integer>();
        Number number = list.get(0);
//        list.add(5); // 编译报错；
//        list.add(new Integer(5)); // 编译报错
//        Integer a = list.get(1); // 编译报错

        List<? super Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(new Integer(1));
//        Number number1 = list1.get(0); // 编译报错；
    }
}

class GlmapperGeneric<T> {
    private T t;

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    /**
     * 不指定类型
     */
    public void noSpecifyType() {
        GlmapperGeneric glmapperGeneric = new GlmapperGeneric();
        glmapperGeneric.set("test");
        // 需要强制类型转换
        String test = (String) glmapperGeneric.get();
        System.out.println(test);
    }

    /**
     * 指定类型
     */
    public void specifyType() {
        GlmapperGeneric<String> glmapperGeneric = new GlmapperGeneric();
        glmapperGeneric.set("test");
        // 不需要强制类型转换
        String test = glmapperGeneric.get();
        System.out.println(test);
    }
}

class Animal {
    int count() {
        return 1;
    }

    static int countLegs(List<? extends Animal> animals) {
        int retVal = 0;
        for (Animal animal : animals) {
            retVal += animal.count();
        }
        return retVal;
    }

    static int countLegs1(List<Animal> animals) {
        int retVal = 0;
        for (Animal animal : animals) {
            retVal += animal.count();
        }
        return retVal;
    }

    public static void main(String[] args) {
        List<Dog> dogs = new ArrayList<>();
        // 不会报错
        countLegs(dogs);
        // 编译报错
//        countLegs1(dogs);
    }
}

class Dog extends Animal {
}
