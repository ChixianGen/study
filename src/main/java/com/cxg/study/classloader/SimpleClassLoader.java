package com.cxg.study.classloader;   // Administrator 于 2019/7/29 创建;

public class SimpleClassLoader {
    public static void main(String[] args) throws ClassNotFoundException {
        test1();
    }

    public static void test1() throws ClassNotFoundException {
        ClassLoader classLoader = SimpleClassLoader.class.getClassLoader();
//        Optional.of(classLoader.getClass().getName()).ifPresent(System.out::println);

        Class<?> aClass = classLoader.loadClass("java.lang.String");
        Class<?> bClass = classLoader.loadClass("java.lang.String");

        System.out.println(aClass.hashCode());
        System.out.println(bClass.hashCode());
    }
}
