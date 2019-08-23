package com.cxg.study.thread.singleton;   // Administrator 于 2019/7/23 创建;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Singleton1 {
    private static Person person;

    // 如果构造器中没有其他复杂操作（需要其他对象引用），则在多线程的情况下，当前方式是安全的。
//    private static Singleton1 singleton;
    //    private Singleton1() {}

    // 真正线程安全的写法，加volatile关键字修饰；下面有解释
    private static volatile Singleton1 singleton;

    private Singleton1() {
        init();
    }

    private void init() {
        this.person = new Person();
    }

    public static Singleton1 getInstance() {
        if (null == singleton) {
            synchronized (Singleton1.class) {
                if (null == singleton) {
                    /**
                     * new一个对象包含如下几个步骤：
                     * 1、在堆内存中为对象分配空间；
                     * 2、空间初始化（对象初始化-执行构造器方法）；
                     * 3、把这个空间的内存地址赋给singleton变量；
                     * 步骤2和3可能会被重排序，导致(singleton != null)，而init()方法为执行完，(person == null)的情况，
                     * 可能为引起调用person的方法时的空指针异常；（暂未模拟出这种情况）
                     * 所以加volatile修饰后会防止指令重排序，才是真的线程安全；
                     */
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
        CountDownLatch countDownLatch = new CountDownLatch(10);
        IntStream.rangeClosed(1, 10).forEach(n -> {
            new Thread(() -> {
                Singleton1 instance = Singleton1.getInstance();
                instance.person.getName();
                Optional.of("线程【" + Thread.currentThread().getName() + "】获取的对象：" + instance)
                        .ifPresent(System.out::println);
                countDownLatch.countDown();
            }, "T_" + n).start();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void getWithVolatile() {
    }
}

@Data
class Person {
    private String name;

    public Person() {
        System.out.println("线程：【" + Thread.currentThread().getName() + "】初始化Person对象");
        try {
            Reader reader = new FileReader("D:\\home\\java\\name.txt");
            BufferedReader br = new BufferedReader(reader);
            Thread.sleep(1000);
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