package com.cxg.study.thread.singleton;   // Administrator 于 2019/7/23 创建;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import java.util.stream.IntStream;

//@Data
public class Singleton1 {

    private Person person;
    private Person person2;
    private Person person3;
    private Person person4;
    private Person person5;

    // 如果构造器中没有其他复杂操作（需要其他对象引用），
    // 则在多线程的情况下，当前方式是安全的。
//    private static Singleton1 singleton;

    // volatile关键字，正确写法
    private static volatile Singleton1 singleton;

    //    private Singleton1() {}
    private Singleton1() {
        init();
    }

    private void init() {
        this.person = new Person();
        this.person2 = new Person();
        this.person3 = new Person();
        this.person4 = new Person();
        this.person5 = new Person();
    }

    public static Singleton1 getInstance() {
        if (null == singleton) {
            synchronized (Singleton1.class) {
                if (null == singleton) {
                    singleton = new Singleton1();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        getWithNoVolatile();
//        getWithVolatile();
    }

    private static void getWithNoVolatile() {
        IntStream.rangeClosed(1, 200).forEach(n -> {
            new Thread("T_" + n) {
                @Override
                public void run() {
                    Singleton1 instance = Singleton1.getInstance();
                    Optional.of("线程【" + currentThread().getName() + "】获取的对象：" + instance)
                            .ifPresent(System.out::println);
                    instance.person.getName();
                    instance.person2.getName();
                    instance.person3.getName();
                    instance.person4.getName();
                    instance.person5.getName();
                }
            }.start();
        });
    }

    private static void getWithVolatile() {
    }
}

@Data
class Person {
    private String name;
    public Person() {
        System.out.println("线程：【" + Thread.currentThread().getName() + "】");
        try {
            Reader reader = new FileReader("D:\\home\\java\\name.txt");
            BufferedReader br = new BufferedReader(reader);
            Thread.sleep(500);
            StringBuilder sb = new StringBuilder();
            String name = null;
            while ((name = br.readLine()) != null) {
                sb.append(name);
            }
            this.name = sb.toString();
            br.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}