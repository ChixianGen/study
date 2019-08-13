/**
 * ThreadLocal线程局部变量
 *
 * @author 马士兵
 */
package com.cxg.study.thread.mashibing.c_022;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

public class ThreadLocal1 {
    /*volatile*/ static Person p = new Person("zhangsan");

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            test(p);
        }
    }

    private static void test(Person p) {
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(p.getName());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.setName("lisi");
        }).start();
    }
}

@Data
@AllArgsConstructor
class Person {
    String name;
}
