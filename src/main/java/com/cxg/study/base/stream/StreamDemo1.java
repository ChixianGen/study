package com.cxg.study.base.stream;   // Administrator 于 2019/8/23 创建;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class StreamDemo1 {
    public static void main(String[] args) {
//        test1();
//        System.out.println("\n------------------------------");
//        test2();
    }

    private static void test2() {
        IntStream.range(1, 10)
                .peek(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        System.out.println("p" + value);
                    }
                })
                .peek(x -> System.out.print("A" + x))
                .limit(3)
                .peek(x -> System.out.print("B" + x))
                .forEach(x -> System.out.print("C" + x + "\n"));
    }

    private static void test1() {
        IntStream.range(1, 10)
                .peek(x -> System.out.println("A" + x))
                .skip(6)
                .peek(x -> System.out.print("B" + x))
                .forEach(x -> System.out.print("C" + x + "\n"));
    }

}
