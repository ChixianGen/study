package com.cxg.study.classloader;   // Administrator 于 2019/7/26 创建;

import lombok.Data;

import java.util.Random;

public class LoaderTest {
    public static void main(String[] args) {
//        new Demo();
//        System.out.println(Demo.getNumber());
//        System.out.println(Demo.NUMBER);
//        Demo.NUMBER = 10;

//        try {
//            Class<?> aClass = Class.forName("com.cxg.study.classloader.Demo");
//                Demo o = (Demo) aClass.newInstance();
//            System.out.println(o.getNick());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }

//        new DemoChild();
        // 以上方式属于类的主动引用；

//        System.out.println(DemoChild.NUMBER);// 只会初始化父类；属于被动引用中的一种；

        // 定义引用数组，不会初始化类；
//        Demo[] demos = new Demo[5];

        // final修饰；final修饰的变量属于常量，或被放入常量池中；（仅包含编译时能直接确定的值）
//        System.out.println(Demo.a);
        // 如果时编译时无法确定的值，需要在运行时去计算。如b，则会导致类的初始化；
//        System.out.println(Demo.b);

//        System.out.println(Demo.getA());
    }
}

@Data
class Demo {
    public static int NUMBER = 5;
    public final String nick = "Demo_nick";
    public final static String a = "aaa";
    public final static int b = new Random().nextInt(10);

    static {
        System.out.printf("Demo类初始化。。。NUMBER:【%s】\n", NUMBER);
    }

    public static int getNumber() {
        System.out.println("调用getNumber方法。。。");
        return NUMBER;
    }

    public static final String getA() {
        System.out.println("执行getA方法。。。");
        return a;
    }
}

class DemoChild extends Demo {
    static {
        System.out.println("DemoChild类初始化。。。");
    }
}

interface Face {
    String name = "cxg";
}
